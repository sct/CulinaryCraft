package net.sctgaming.baconpancakes.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;

public class ItemCookedEgg extends ItemFood {

	public ItemCookedEgg(int id) {
		super(id, 4, true);
		setMaxStackSize(64);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.cookedegg");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("cookedEgg");
	}
}
