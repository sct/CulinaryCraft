package sct.culinarycraft.item;

import sct.culinarycraft.gui.BaconPancakesCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBacon extends ItemFood {

	public ItemBacon(int id) {
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
