package sct.culinarycraft.block;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.core.ITankContainerBucketable;
import sct.culinarycraft.core.MFRLiquidMover;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import sct.culinarycraft.tile.TileEntityMachine;
import sct.culinarycraft.util.CulinaryCraftUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerRegistry;

public abstract class BlockMachine extends BlockContainer {

	public BlockMachine(int id) {
		super(id, Material.rock);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null || player.isSneaking()) {
			return false;
		}
		
		if (te instanceof ITankContainerBucketable 
				&& LiquidContainerRegistry.isEmptyContainer(player.inventory.getCurrentItem()) 
				&& ((ITankContainerBucketable)te).allowBucketDrain()) {
			if (MFRLiquidMover.manuallyDrainTank((ITankContainerBucketable) te, player)) {
				return true;
			}
		} else if (te instanceof ITankContainerBucketable
				&& LiquidContainerRegistry.isFilledContainer(player.inventory.getCurrentItem())
				&& ((ITankContainerBucketable)te).allowBucketFill()) {
			if (MFRLiquidMover.manuallyFillTank((ITankContainerBucketable) te, player)) {
				return true;
			}
		} else if (CulinaryCraftUtils.isHoldingMixer(player) && ((TileEntityMachine) te).canRotate()) {
			((TileEntityMachine) te).rotate();
		} else {
			player.openGui(CulinaryCraft.instance, 0, world, x, y, z);
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
