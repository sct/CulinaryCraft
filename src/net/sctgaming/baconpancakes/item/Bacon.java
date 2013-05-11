package net.sctgaming.baconpancakes.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Bacon extends ItemFood {

	public Bacon(int id) {
		super(id, 4, true);
		setMaxStackSize(64);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sctbacon");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("bacon");
	}

}
