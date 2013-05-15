package sct.culinarycraft.block.crop;

import sct.culinarycraft.CulinaryCraft;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class CropCoffea extends CropBase {

	public CropCoffea(int id) {
		super(id, 5);
		setUnlocalizedName("culinary.crop.coffea");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < getGrowthStages(); i++) {
			icons[i] = iconRegister.registerIcon("coffeaCrop_" + i);
		}
	}

	@Override
	protected int getCropItem() {
		return CulinaryCraft.seedCoffeaSeed.itemID;
	}

	@Override
	protected int getSeedItem() {
		return CulinaryCraft.seedCoffeaSeed.itemID;
	}

}
