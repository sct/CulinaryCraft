package net.sctgaming.baconpancakes.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sctgaming.baconpancakes.BaconPancakes;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import net.sctgaming.baconpancakes.tile.TileEntityOven;
import net.sctgaming.baconpancakes.util.BasicUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOven extends BlockContainer {
	
	private Icon[] icons = new Icon[6];

	public BlockOven(int id) {
		super(id, Material.rock);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.oven");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		icons[0] = ir.registerIcon("tile.sct.oven.idle.side");
		icons[1] = ir.registerIcon("tile.sct.oven.idle.top");
		icons[2] = ir.registerIcon("tile.sct.oven.idle.front");
		icons[3] = ir.registerIcon("tile.sct.oven.idle.side");
		icons[4] = ir.registerIcon("tile.sct.oven.idle.side");
		icons[5] = ir.registerIcon("tile.sct.oven.idle.side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x,
			int y, int z, int side) {
		int md = iBlockAccess.getBlockMetadata(x, y, z);
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityOven) {
			side = ((TileEntityOven) te).getRotatedSide(side);
		}
		return this.getIcon(side, md);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side > 1)
		{
			side += 2;
			if (side > 5)
			{
				side -= 4;
			}
		}
		return icons[side];
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityOven();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		if (BasicUtils.isHoldingMixer(player) && ((TileEntityOven) tileEntity).canRotate()) {
			((TileEntityOven) tileEntity).rotate();
		} else {
			player.openGui(BaconPancakes.instance, 0, world, x, y, z);
		}
		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z,
			int par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		
		IInventory inventory = (IInventory) tileEntity;
		
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);
			
			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, 
						new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
				
				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				
				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
				
			}
		}
	}

}
