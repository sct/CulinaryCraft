package net.sctgaming.baconpancakes.item.seed;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.common.IPlantable;
import net.sctgaming.baconpancakes.BaconPancakes;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlackPeppercorn extends ItemSeeds implements IPlantable {

	public ItemBlackPeppercorn(int id) {
		super(id, BaconPancakes.cropBlackPepper.blockID, Block.tilledField.blockID);
		setMaxStackSize(64);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.blackpeppercorn");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("blackPeppercorn");
	}

}
