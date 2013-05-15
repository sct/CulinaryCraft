package sct.culinarycraft.block.crop;

import net.minecraft.client.renderer.texture.IconRegister;
import sct.culinarycraft.CulinaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropBlackPepper extends CropBase {

	public CropBlackPepper(int id) {
		super(id, 8);
		setUnlocalizedName("sct.crop.blackpepper");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < getGrowthStages(); i++) {
			icons[i] = iconRegister.registerIcon("blackPepperCrop_" + i);
		}
	}
	
	@Override
	protected int getSeedItem() {
		return CulinaryCraft.seedBlackPeppercorn.itemID;
	}

	@Override
	protected int getCropItem() {
		return CulinaryCraft.seedBlackPeppercorn.itemID;
	}
}
