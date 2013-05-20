package sct.culinarycraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBucket;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWineBucket extends ItemBucket {

	public ItemWineBucket(int id, int liquidId) {
		super(id, liquidId);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("bucket.wine");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		itemIcon = ir.registerIcon("bucketWine");
	}

}
