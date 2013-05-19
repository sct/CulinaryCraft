package sct.culinarycraft.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.tile.TileEntityCrop;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CropRenderer extends TileEntitySpecialRenderer implements
		ISimpleBlockRenderingHandler {

	private int renderId;

	public CropRenderer(int renderId) {
		this.renderId = renderId;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float f) {
		
		int md = 0;
		if (((TileEntityCrop) te).getHeight() > 1) {
			if (te.worldObj.getBlockId(te.xCoord, te.yCoord - (((TileEntityCrop) te).getHeight() - 1), te.zCoord) == 0) {
				return;
			}
			md = te.worldObj.getBlockMetadata(te.xCoord, te.yCoord - (((TileEntityCrop) te).getHeight() - 1), te.zCoord);
		} else {
			md = te.worldObj.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);
		}
		
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine
				.bindTexture("/terrain.png");
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 1.0F, 1.0F);
		
		Icon icon = ((TileEntityCrop) te).getIcon(md);

		tessellator.setBrightness(CulinaryCraft.crop
				.getMixedBrightnessForBlock((IBlockAccess) te.worldObj,
						(int) te.xCoord, (int) te.yCoord, (int) te.zCoord));
		int l = CulinaryCraft.crop.colorMultiplier((IBlockAccess) te.worldObj,
				(int) te.xCoord, (int) te.yCoord, (int) te.zCoord);
		float f0 = 1.0F;
		float f1 = (float) (l >> 16 & 255) / 255.0F;
		float f2 = (float) (l >> 8 & 255) / 255.0F;
		float f3 = (float) (l & 255) / 255.0F;

		tessellator.setColorOpaque_F(f0 * f1, f0 * f2, f0 * f3);

		double minU = (double) icon.getMinU();
		double minV = (double) icon.getMinV();
		double maxU = (double) icon.getMaxU();
		double maxV = (double) icon.getMaxV();

		double df = 0.45D * (double) 1.0F;
		double newdX = x + 0.5D - df;
		double newdX2 = x + 0.5D + df;
		double newdZ = z + 0.5D - df;
		double newdZ2 = z + 0.5D + df;
		tessellator.addVertexWithUV(newdX, y + (double) 1.0F, newdZ, minU, minV);
		tessellator.addVertexWithUV(newdX, y + 0.0D, newdZ, minU, maxV);
		tessellator.addVertexWithUV(newdX2, y + 0.0D, newdZ2, maxU, maxV);
		tessellator.addVertexWithUV(newdX2, y + (double) 1.0F, newdZ2, maxU,
				minV);
		tessellator.addVertexWithUV(newdX2, y + (double) 1.0F, newdZ2, minU,
				minV);
		tessellator.addVertexWithUV(newdX2, y + 0.0D, newdZ2, minU, maxV);
		tessellator.addVertexWithUV(newdX, y + 0.0D, newdZ, maxU, maxV);
		tessellator.addVertexWithUV(newdX, y + (double) 1.0F, newdZ, maxU, minV);
		tessellator.addVertexWithUV(newdX, y + (double) 1.0F, newdZ2, minU,
				minV);
		tessellator.addVertexWithUV(newdX, y + 0.0D, newdZ2, minU, maxV);
		tessellator.addVertexWithUV(newdX2, y + 0.0D, newdZ, maxU, maxV);
		tessellator.addVertexWithUV(newdX2, y + (double) 1.0F, newdZ, maxU,
				minV);
		tessellator.addVertexWithUV(newdX2, y + (double) 1.0F, newdZ, minU,
				minV);
		tessellator.addVertexWithUV(newdX2, y + 0.0D, newdZ, minU, maxV);
		tessellator.addVertexWithUV(newdX, y + 0.0D, newdZ2, maxU, maxV);
		tessellator.addVertexWithUV(newdX, y + (double) 1.0F, newdZ2, maxU,
				minV);

		tessellator.draw();
		GL11.glPopMatrix();
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}

}
