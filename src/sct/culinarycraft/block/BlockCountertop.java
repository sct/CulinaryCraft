package sct.culinarycraft.block;

import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityMachine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCountertop extends BlockMachineInventory {
	
	private Icon[] icons = new Icon[6];

	public BlockCountertop(int id) {
		super(id);
		setUnlocalizedName("culinary.machine.countertop");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		icons[0] = ir.registerIcon("tile.sct.countertop.idle.side");
		icons[1] = ir.registerIcon("tile.sct.countertop.idle.top");
		icons[2] = ir.registerIcon("tile.sct.countertop.idle.side");
		icons[3] = ir.registerIcon("tile.sct.countertop.idle.front");
		icons[4] = ir.registerIcon("tile.sct.countertop.idle.side");
		icons[5] = ir.registerIcon("tile.sct.countertop.idle.side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x,
			int y, int z, int side) {
		int md = iBlockAccess.getBlockMetadata(x, y, z);
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMachine) {
			side = ((TileEntityMachine) te).getRotatedSide(side);
		}
		return this.getIcon(side, md);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icons[side];
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCountertop();
	}

}
