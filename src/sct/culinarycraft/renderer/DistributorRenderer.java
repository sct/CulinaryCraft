package sct.culinarycraft.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import sct.culinarycraft.model.ModelHydroponicDistributor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DistributorRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	
	private int renderId;
	private String textureFile = "/textures/maps/HydroponicDistributor.png";
	private ModelHydroponicDistributor distributor = new ModelHydroponicDistributor();

	public DistributorRenderer(int renderId) {
		this.renderId = renderId;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		
		GL11.glRotatef(180F, 1, 0, 0);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureFile);
		GL11.glPushMatrix();
		distributor.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glScalef(.65F, .65F, .65F);
		GL11.glTranslatef(0, 0.6F, 0);
		GL11.glRotatef(180F, 1, 0, 0);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(textureFile);
		GL11.glPushMatrix();
		distributor.render(0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}

}
