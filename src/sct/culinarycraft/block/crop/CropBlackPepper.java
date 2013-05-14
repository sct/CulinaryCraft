package sct.culinarycraft.block.crop;

import java.util.ArrayList;

import sct.culinarycraft.CulinaryCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropBlackPepper extends BlockCrops {
	
	private Icon[] icons = new Icon[8];

	public CropBlackPepper(int id) {
		super(id);
		setUnlocalizedName("sct.crop.blackpepper");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < 8; i++) {
			icons[i] = iconRegister.registerIcon("blackPepperCrop_" + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta > 7 || meta < 0) {
			meta = 7;
		}
		
		return icons[meta];
	}
	
	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int blockId) {
		return blockId == Block.tilledField.blockID;
	}
	
	@Override
	protected int getSeedItem() {
		return CulinaryCraft.seedBlackPeppercorn.itemID;
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		ArrayList<ItemStack> blocks = new ArrayList<ItemStack>();
		
		if (metadata >= 7) {
			for (int n = 0; n < 2 + fortune; n++) {
				if (world.rand.nextInt(12) <= metadata) {
					blocks.add(new ItemStack(getSeedItem(), 1, 0));
				}
			}
		}
		
		return blocks;
	}
	
	@Override
	public int getRenderType() {
		return 1;
	}

}
