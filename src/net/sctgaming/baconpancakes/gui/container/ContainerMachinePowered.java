package net.sctgaming.baconpancakes.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.tileentity.TileEntity;
import net.sctgaming.baconpancakes.tile.TileEntityMachinePowered;
import net.sctgaming.baconpancakes.tile.TileEntityOven;

public abstract class ContainerMachinePowered extends Container {
	
	protected TileEntity te;
	
	public ContainerMachinePowered(TileEntity te) {
		this.te = te;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < crafters.size(); i++) {
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 0, ((TileEntityMachinePowered)te).getEnergyStored());
			((ICrafting)crafters.get(i)).sendProgressBarUpdate(this, 1, ((TileEntityOven)te).getCookTime());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value) {
		super.updateProgressBar(var, value);
		
		if (var == 0) {
			((TileEntityMachinePowered)te).setEnergyStored(value);
		} else if (var == 1) {
			((TileEntityOven) te).setCookTime(value);
		}
	}
	
}