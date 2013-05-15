package sct.culinarycraft.item.seed;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemSeeds;

public class ItemCoffeaSeed extends ItemSeeds {

	public ItemCoffeaSeed(int id) {
		super(id, CulinaryCraft.cropCoffea.blockID, Block.tilledField.blockID);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.seed.coffea");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("coffeaSeed");
	}
	
	
	
}
