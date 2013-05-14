package sct.culinarycraft.gui.container;

import sct.culinarycraft.tile.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerOven extends ContainerMachinePowered {
	
	public ContainerOven(InventoryPlayer invPlayer, TileEntityOven te) {
		super(te);
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				addSlotToContainer(new OvenSlot(te, y + x * 3, 8 + y * 18, 17 + x * 18));
			}
		}
		
		this.addSlotToContainer(new SlotFurnace(invPlayer.player, (IInventory) te, 9, 124, 35));
		
		bindPlayerInventory(invPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < crafters.size(); i++) {
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, ((TileEntityOven)te).getCookTime());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value) {
		super.updateProgressBar(var, value);
		
		if (var == 1) {
			((TileEntityOven) te).setCookTime(value);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((TileEntityOven) te).isUseableByPlayer(player);
	}
	
	protected void bindPlayerInventory(InventoryPlayer invPlayer) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 9; y++) {
				addSlotToContainer(new Slot(invPlayer, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
			}
		}
		
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			
			if (slot < 8) {
				if (!this.mergeItemStack(stackInSlot, 9, 45, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
				return null;
			}
			
			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
			
			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		
		return stack;
	}

}
