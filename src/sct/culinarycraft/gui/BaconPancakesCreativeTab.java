package sct.culinarycraft.gui;

import sct.culinarycraft.CulinaryCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BaconPancakesCreativeTab extends CreativeTabs {
	
	public static final BaconPancakesCreativeTab tab = new BaconPancakesCreativeTab("Bacon Pancakes");

	public BaconPancakesCreativeTab(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(CulinaryCraft.pancakes, 1, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return this.getTabLabel();
	}

}
