package sct.culinarycraft.item.seed;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlackPeppercorn extends ItemSeeds implements IPlantable {

	public ItemBlackPeppercorn(int id) {
		super(id, CulinaryCraft.cropBlackPepper.blockID, Block.tilledField.blockID);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("sct.blackpeppercorn");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("blackPeppercorn");
	}

}
