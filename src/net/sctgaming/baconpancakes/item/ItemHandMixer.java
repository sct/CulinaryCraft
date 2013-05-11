package net.sctgaming.baconpancakes.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHandMixer extends Item {

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
