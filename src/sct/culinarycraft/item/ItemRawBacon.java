package sct.culinarycraft.item;

import sct.culinarycraft.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

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
