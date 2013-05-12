package net.sctgaming.baconpancakes.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;

public class ItemRawBacon extends ItemFood {

	public ItemRawBacon(int id) {
		super(id, 1, false);
		setMaxStackSize(64);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.rawbacon");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("rawBacon");
	}

}
