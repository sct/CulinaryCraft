package net.sctgaming.baconpancakes.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;

public class ItemSalt extends Item {

	public ItemSalt(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.salt");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("salt");
	}


}
