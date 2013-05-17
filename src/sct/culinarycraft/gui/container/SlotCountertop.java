package sct.culinarycraft.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sct.culinarycraft.item.ItemKitchenTool;
import sct.culinarycraft.tile.TileEntityCountertop;

public class SlotCountertop extends Slot {
	
	private boolean isToolSlot = false;

	public SlotCountertop(IInventory par1iInventory, int par2, int par3,
			int par4, boolean isToolSlot) {
		super(par1iInventory, par2, par3, par4);
		this.isToolSlot = isToolSlot;
	}
	
	@Override
	public boolean isItemValid(ItemStack is) {
		return is.getItem() instanceof ItemKitchenTool || !isToolSlot();
	}
	
	public boolean isToolSlot() {
		return isToolSlot;
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (isToolSlot()) {
			if (getStack() != null)
				((TileEntityCountertop) this.inventory).setTool(getStack().itemID);
			else
				((TileEntityCountertop) this.inventory).setTool(0);
		}
		
		((TileEntityCountertop) this.inventory).checkRecipes();
	}

}
