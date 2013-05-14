package net.sctgaming.baconpancakes.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.sctgaming.baconpancakes.model.ModelCuttingBoard;
import net.sctgaming.baconpancakes.tile.TileEntityMachine;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class CounterRenderer extends TileEntitySpecialRenderer {
	
	private ModelCuttingBoard board = new ModelCuttingBoard();

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float f) {
		int l = te.worldObj.getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord + 1, te.zCoord, 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i1, (float)j1);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.65F, (float) z + 0.5F);
		
		float angle = 0;
		switch (((TileEntityMachine) te).getForwardDirection()) {
			case NORTH:
				angle = 0F;
				break;
			case EAST:
				angle = 90F;
				break;
			case SOUTH:
				angle = 180F;
				break;
			case WEST:
				angle = -90F;
				break;
			default:
				angle = 0;
				break;
		}
		GL11.glRotatef(angle, 0, 1, 0);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/textures/maps/CuttingBoard.png");
		GL11.glPushMatrix();
		board.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
