package sct.culinarycraft.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import sct.culinarycraft.item.ItemCuttingBoard;
import sct.culinarycraft.item.ItemStandMixer;
import sct.culinarycraft.model.ModelCuttingBoard;
import sct.culinarycraft.model.ModelStandMixer;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityMachine;
import cpw.mods.fml.client.FMLClientHandler;

public class CounterRenderer extends TileEntitySpecialRenderer {
	
	private ModelCuttingBoard board = new ModelCuttingBoard();
	private ModelStandMixer mixer = new ModelStandMixer();

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float f) {
		if (((TileEntityCountertop) te).getTool() != null) {
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
					angle = 180F;
					break;
				case EAST:
					angle = 90F;
					break;
				case SOUTH:
					angle = 0F;
					break;
				case WEST:
					angle = -90F;
					break;
				default:
					angle = 0;
					break;
			}
			GL11.glRotatef(angle, 0, 1, 0);
			if (((TileEntityCountertop) te).getTool() instanceof ItemCuttingBoard) {
				FMLClientHandler.instance().getClient().renderEngine.bindTexture("/textures/maps/CuttingBoard.png");
				GL11.glPushMatrix();
				board.render(0.0625F);
			} else if (((TileEntityCountertop) te).getTool() instanceof ItemStandMixer) {
				GL11.glTranslatef(0, 0.80F, 0);
				GL11.glRotatef(180F, 0, 0, 1);
				FMLClientHandler.instance().getClient().renderEngine.bindTexture("/textures/maps/StandMixer.png");
				GL11.glPushMatrix();
				mixer.render(0.0625F);
			} else {
				GL11.glPushMatrix();
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}

}
