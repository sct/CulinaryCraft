package net.sctgaming.baconpancakes.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.sctgaming.baconpancakes.gui.client.GuiDehydrator;
import net.sctgaming.baconpancakes.gui.client.GuiOven;
import net.sctgaming.baconpancakes.gui.container.ContainerDehydrator;
import net.sctgaming.baconpancakes.gui.container.ContainerOven;
import net.sctgaming.baconpancakes.tile.TileEntityDehydrator;
import net.sctgaming.baconpancakes.tile.TileEntityOven;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityOven) {
			return new ContainerOven(player.inventory, (TileEntityOven) tileEntity);
		} else if (tileEntity instanceof TileEntityDehydrator) {
			return new ContainerDehydrator(player.inventory, (TileEntityDehydrator) tileEntity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityOven) {
			return new GuiOven(player.inventory, (TileEntityOven) tileEntity);
		} else if (tileEntity instanceof TileEntityDehydrator) {
			return new GuiDehydrator(player.inventory, (TileEntityDehydrator) tileEntity);
		}
		return null;
	}

}
