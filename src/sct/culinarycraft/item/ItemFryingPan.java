package sct.culinarycraft.item;

import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFryingPan extends ItemKitchenTool {

	public ItemFryingPan(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.fryingpan");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("fryingPan");
	}

}
