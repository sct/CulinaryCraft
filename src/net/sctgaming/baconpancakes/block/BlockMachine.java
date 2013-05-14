package net.sctgaming.baconpancakes.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.sctgaming.baconpancakes.BaconPancakes;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import net.sctgaming.baconpancakes.tile.TileEntityMachine;
import net.sctgaming.baconpancakes.util.BaconPancakesUtils;

public abstract class BlockMachine extends BlockContainer {

	public BlockMachine(int id) {
		super(id, Material.rock);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(BaconPancakesCreativeTab.tab);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		if (BaconPancakesUtils.isHoldingMixer(player) && ((TileEntityMachine) tileEntity).canRotate()) {
			((TileEntityMachine) tileEntity).rotate();
		} else {
			player.openGui(BaconPancakes.instance, 0, world, x, y, z);
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack stack)
	{
		if(entity == null)
		{
			return;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(stack.getTagCompound() != null)
		{
			stack.getTagCompound().setInteger("x", x);
			stack.getTagCompound().setInteger("y", y);
			stack.getTagCompound().setInteger("z", z);
			te.readFromNBT(stack.getTagCompound());
		}

		if(te instanceof TileEntityMachine && ((TileEntityMachine)te).canRotate())
		{
			int facing = MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
			if(facing == 0)
			{
				((TileEntityMachine)te).rotateDirectlyTo(3);
			}
			else if(facing == 1)
			{
				((TileEntityMachine)te).rotateDirectlyTo(4);
			}
			else if(facing == 2)
			{
				((TileEntityMachine)te).rotateDirectlyTo(2);
			}
			else if(facing == 3)
			{
				((TileEntityMachine)te).rotateDirectlyTo(5);
			}
		}
	}

}
