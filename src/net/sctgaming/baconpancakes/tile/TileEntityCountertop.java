package net.sctgaming.baconpancakes.tile;

public class TileEntityCountertop extends TileEntityMachinePowered {

	public TileEntityCountertop() {
		super(3);
	}
	
	@Override
	public boolean canRotate() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public int getEnergyStoredMax() {
		return 500;
	}

	@Override
	public int getIdleTicksMax() {
		return 20;
	}

}
