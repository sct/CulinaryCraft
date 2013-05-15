package sct.culinarycraft.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sct.culinarycraft.tile.TileEntityHydroponicDistributor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHydroponicDistributor extends BlockMachine {
	
	private int renderType;

	public BlockHydroponicDistributor(int id) {
		super(id);
		setUnlocalizedName("sct.distributor");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		blockIcon = ir.registerIcon("distributor");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4, int par5) {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityHydroponicDistributor();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityHydroponicDistributor) {
			((TileEntityHydroponicDistributor) te).findConnectedResevoirs(x,y,z);
		}
	}
	
	@Override
	public void onBlockHarvested(World world, int x, int y, int z,
			int par5, EntityPlayer par6EntityPlayer) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityHydroponicDistributor) {
			((TileEntityHydroponicDistributor) te).disconnectNetwork();
		}
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}
	
	public void setRenderType(int id) {
		this.renderType = id;
	}
	
}
