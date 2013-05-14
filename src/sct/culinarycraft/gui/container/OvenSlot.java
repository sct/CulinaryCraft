package sct.culinarycraft.gui.container;

import sct.culinarycraft.tile.TileEntityOven;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class OvenSlot extends Slot {

	public OvenSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public void onSlotChanged() {
		((TileEntityOven) this.inventory).checkRecipes();
	}

}
