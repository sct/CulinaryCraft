package sct.culinarycraft.item;

import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

public class ItemCookedEgg extends ItemFood {

	public ItemCookedEgg(int id) {
		super(id, 4, true);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("sct.cookedegg");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("cookedEgg");
	}
}
