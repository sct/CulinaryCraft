package net.sctgaming.baconpancakes.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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

}
