package sct.culinarycraft.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sct.culinarycraft.item.ItemKitchenTool;
import sct.culinarycraft.tile.TileEntityCountertop;

public class SlotCountertop extends Slot {

	public SlotCountertop(IInventory par1iInventory, int par2, int par3,
			int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack is) {
		return is.getItem() instanceof ItemKitchenTool;
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (getStack() != null)
			((TileEntityCountertop) this.inventory).setTool(getStack().itemID);
		else
			((TileEntityCountertop) this.inventory).setTool(0);
	}

}
