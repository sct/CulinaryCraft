package sct.culinarycraft.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import sct.culinarycraft.crop.Crop;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import sct.culinarycraft.tile.TileEntityCrop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropBase extends BlockCrops implements ITileEntityProvider {

	private int renderType = 0;

	public CropBase(int id) {
		super(id);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("crop");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		Crop.loadTextures(ir);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return Crop.getCropFromIndex(0).getIcon(0);
	}
	
	public Icon getSeedIcon(int index) {
		return Crop.getCropFromIndex(index).getIcon(0);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCrop) {
			if (world.getBlockLightValue(x, y + 1, z) >= 9 && ((TileEntityCrop) te).getHeight() == 1) {
				int md = world.getBlockMetadata(x, y, z);
				
				int stages = ((TileEntityCrop) te).getCrop().getStages();
				if (md < (stages - 1)) {
					float f = getGrowthRate(world, x, y, z);

					if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
						int height = ((TileEntityCrop) te).getCrop().getHeight();
						if (height > 1 && ((md + 1) % (stages / height)) == 0) {
							boolean placed = false;
							int attempt = 1;
							while (!placed) {
								if (world.getBlockId(x, y + attempt, z) != this.blockID) {
									world.setBlock(x, y + attempt, z, this.blockID);
									world.setBlockMetadataWithNotify(x, y + attempt, z, md + 1, 2);
									TileEntity newEntity = world.getBlockTileEntity(x, y + attempt, z);
									if (newEntity != null && newEntity instanceof TileEntityCrop) {
										((TileEntityCrop) newEntity).setCrop(((TileEntityCrop) te).getCrop());
										((TileEntityCrop) newEntity).setHeight(attempt + 1);
									}
									
									placed = true;
								}
								
								attempt++;
							}
						}
						++md;
						world.setBlockMetadataWithNotify(x, y, z, md, 2);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canSustainPlant(World world, int x, int y, int z,
			ForgeDirection direction, IPlantable plant) {
		if (world.getBlockId(x, y - 1, z) == world.getBlockId(x, y, z)) return true;
		
		return super.canSustainPlant(world, x, y, z, direction, plant);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		if (world.getBlockId(x, y - 1, z) == world.getBlockId(x, y, z)) return true;
		
		return super.canBlockStay(world, x, y, z);
	}

	private float getGrowthRate(World par1World, int par2, int par3, int par4) {
		float f = 1.0F;
		int l = par1World.getBlockId(par2, par3, par4 - 1);
		int i1 = par1World.getBlockId(par2, par3, par4 + 1);
		int j1 = par1World.getBlockId(par2 - 1, par3, par4);
		int k1 = par1World.getBlockId(par2 + 1, par3, par4);
		int l1 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
		int i2 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
		int j2 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
		int k2 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
		boolean flag = j1 == this.blockID || k1 == this.blockID;
		boolean flag1 = l == this.blockID || i1 == this.blockID;
		boolean flag2 = l1 == this.blockID || i2 == this.blockID
				|| j2 == this.blockID || k2 == this.blockID;

		for (int l2 = par2 - 1; l2 <= par2 + 1; ++l2) {
			for (int i3 = par4 - 1; i3 <= par4 + 1; ++i3) {
				int j3 = par1World.getBlockId(l2, par3 - 1, i3);
				float f1 = 0.0F;

				if (blocksList[j3] != null
						&& blocksList[j3].canSustainPlant(par1World, l2,
								par3 - 1, i3, ForgeDirection.UP, this)) {
					f1 = 1.0F;

					if (blocksList[j3].isFertile(par1World, l2, par3 - 1, i3)) {
						f1 = 3.0F;
					}
				}

				if (l2 != par2 || i3 != par4) {
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		if (flag2 || flag && flag1) {
			f /= 2.0F;
		}

		return f;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y,
			int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = super.getBlockDropped(world, x, y, z,
				metadata, fortune);

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCrop) {
			if (metadata >= (((TileEntityCrop) te).getCrop().getStages() - 1)) {
				for (int n = 0; n < 3 + fortune; n++) {
					if (world.rand.nextInt(15) <= metadata) {
						ret.add(new ItemStack(Item.itemsList[((TileEntityCrop) te).getCrop().getCropId()], 1, 0));
					}
				}
			}
		}

		return ret;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z,
			int par5, int par6) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCrop && ((TileEntityCrop) te).getCrop().getHeight() > 1) {
			((TileEntityCrop) te).destroyStalk();
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3,
			int par4, int par5, int par6) {
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6)
				: false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLiving entity, ItemStack stack) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityCrop) {
			((TileEntityCrop) te).setCropByIndex(stack.getItemDamage());
		}
	}

	@Override
	public int getRenderType() {
		return renderType;
	}

	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int blockId) {
		return blockId == Block.tilledField.blockID;
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	protected int getCropItem() {
		return 0;
	}

	@Override
	protected int getSeedItem() {
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCrop();
	}

}
