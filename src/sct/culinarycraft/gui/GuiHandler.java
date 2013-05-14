package sct.culinarycraft.gui;

import sct.culinarycraft.gui.client.GuiCountertop;
import sct.culinarycraft.gui.client.GuiDehydrator;
import sct.culinarycraft.gui.client.GuiOven;
import sct.culinarycraft.gui.container.ContainerCountertop;
import sct.culinarycraft.gui.container.ContainerDehydrator;
import sct.culinarycraft.gui.container.ContainerOven;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityDehydrator;
import sct.culinarycraft.tile.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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
		} else if (tileEntity instanceof TileEntityCountertop) {
			return new ContainerCountertop(player.inventory, (TileEntityCountertop) tileEntity);
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
		} else if (tileEntity instanceof TileEntityCountertop) {
			return new GuiCountertop(player.inventory, (TileEntityCountertop) tileEntity);
		}
		return null;
	}

}
