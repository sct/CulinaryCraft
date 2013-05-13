package net.sctgaming.baconpancakes.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.sctgaming.baconpancakes.tile.TileEntityDehydrator;
import net.sctgaming.baconpancakes.tile.TileEntityMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDehydrator extends BlockMachineInventory {
	
	private Icon[] icons = new Icon[6];
	private Icon[] iconsWorking = new Icon[6];

	public BlockDehydrator(int id) {
		super(id);
		setUnlocalizedName("sct.dehydrator");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		icons[0] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[1] = ir.registerIcon("tile.sct.dehydrator.idle.top");
		icons[2] = ir.registerIcon("tile.sct.dehydrator.idle.front");
		icons[3] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[4] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[5] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		
		iconsWorking[0] = ir.registerIcon("tile.sct.dehydrator.active.side");
		iconsWorking[1] = ir.registerIcon("tile.sct.dehydrator.active.top");
		iconsWorking[2] = ir.registerIcon("tile.sct.dehydrator.active.front");
		iconsWorking[3] = ir.registerIcon("tile.sct.dehydrator.active.side");
		iconsWorking[4] = ir.registerIcon("tile.sct.dehydrator.active.side");
		iconsWorking[5] = ir.registerIcon("tile.sct.dehydrator.active.side");
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x,
			int y, int z, int side) {
		boolean isWorking = false;
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		
		if (te instanceof TileEntityMachine) {
			side = ((TileEntityMachine) te).getRotatedSide(side);
			isWorking = ((TileEntityMachine)te).isWorking();
		}
		return this.getIcon(side, isWorking);
	}
	
	public Icon getIcon(int side, boolean isWorking) {
		if (isWorking) return iconsWorking[side];
		return icons[side];
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
		return new TileEntityDehydrator();
	}

}
