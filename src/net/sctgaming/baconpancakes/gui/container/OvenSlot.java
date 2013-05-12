package net.sctgaming.baconpancakes.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.sctgaming.baconpancakes.tile.TileEntityOven;

public class OvenSlot extends Slot {

	public OvenSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public void onSlotChanged() {
		((TileEntityOven) this.inventory).checkRecipes();
	}

}
