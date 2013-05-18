package sct.culinarycraft.tile;

import net.minecraft.tileentity.TileEntity;
import sct.culinarycraft.crop.Crop;

public class TileEntityCrop extends TileEntity {
	private Crop crop = null;
	
	public TileEntityCrop() {
		this.crop = Crop.coffea;
	}
	
	public Crop getCrop() {
		return crop;
	}
	
	public void setCrop(Crop crop) {
		this.crop = crop;
	}
}
