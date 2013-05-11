package net.sctgaming.baconpancakes.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.sctgaming.baconpancakes.gui.container.ContainerOven;
import net.sctgaming.baconpancakes.tile.TileEntityOven;

import org.lwjgl.opengl.GL11;

public class GuiOven extends GuiContainer {

	public GuiOven(InventoryPlayer invPlayer, TileEntityOven te) {
		super(new ContainerOven(invPlayer, te));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString("Oven", 8, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture("/textures/gui/oven.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
