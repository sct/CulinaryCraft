package sct.culinarycraft.item;

import sct.culinarycraft.gui.BaconPancakesCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHandMixer extends ItemKitchenTool {

	public ItemHandMixer(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.handmixer");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("handMixer");
	}

}
