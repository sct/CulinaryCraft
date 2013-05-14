package sct.culinarycraft.gui.container;

import sct.culinarycraft.tile.TileEntityDehydrator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerDehydrator extends ContainerMachinePowered {
	
	private int tempLiquidId;
	private int tempLiquidMeta;

	public ContainerDehydrator(InventoryPlayer invPlayer, TileEntityDehydrator te) {
		super(te);
		
		this.addSlotToContainer(new SlotFurnace(invPlayer.player, (IInventory) te, 0, 80, 34));
		
		bindPlayerInventory(invPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((TileEntityDehydrator) te).isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		LiquidStack liquid = ((TileEntityDehydrator) te).getTank().getLiquid();
		
		for (int i = 0; i < crafters.size(); i++) {
			if (liquid != null) {
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, liquid.itemID);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 2, liquid.itemMeta);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, liquid.amount);
			} else {
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 2, 0);
				((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 3, 0);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value) {
		super.updateProgressBar(var, value);
		
		if(var == 1) tempLiquidId = value;
		else if(var == 2) tempLiquidMeta = value;
		else if(var == 3)
		{
			((LiquidTank) ((TileEntityDehydrator)te).getTanks(ForgeDirection.UNKNOWN)[0]).setLiquid(new LiquidStack(tempLiquidId, value, tempLiquidMeta));
		}
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
