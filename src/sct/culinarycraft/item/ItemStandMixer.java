package sct.culinarycraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStandMixer extends ItemKitchenTool {

	public ItemStandMixer(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("sct.standmixer");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("standMixer");
	}

}
