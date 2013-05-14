package sct.culinarycraft.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import sct.culinarycraft.tile.TileEntityCountertop;

public class ContainerCountertop extends ContainerMachinePowered {

	public ContainerCountertop(InventoryPlayer invPlayer, TileEntityCountertop te) {
		super(te);
		
		addSlotToContainer(new SlotCountertop(te, 0, 26, 34));
		addSlotToContainer(new Slot(te, 1, 80, 34));
		this.addSlotToContainer(new SlotFurnace(invPlayer.player, (IInventory) te, 2, 115, 34));
		
		bindPlayerInventory(invPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((TileEntityCountertop) te).isUseableByPlayer(player);
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
				if (!this.mergeItemStack(stackInSlot, 1, 45, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(stackInSlot, 0, 1, false)) {
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
