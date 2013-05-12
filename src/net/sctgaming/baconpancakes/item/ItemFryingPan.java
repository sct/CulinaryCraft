package net.sctgaming.baconpancakes.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFryingPan extends ItemKitchenTool {

	public ItemFryingPan(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.fryingpan");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("fryingPan");
	}

}
