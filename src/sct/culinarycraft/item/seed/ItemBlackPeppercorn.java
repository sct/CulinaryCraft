package sct.culinarycraft.item.seed;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlackPeppercorn extends Item {

	public ItemBlackPeppercorn(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.seed.blackpeppercorn");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("blackPeppercorn");
	}

}
