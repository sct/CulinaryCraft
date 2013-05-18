package sct.culinarycraft;

import sct.culinarycraft.block.BlockHydroponicDistributor;
import sct.culinarycraft.block.CropBase;
import sct.culinarycraft.renderer.CounterRenderer;
import sct.culinarycraft.renderer.CropRenderer;
import sct.culinarycraft.renderer.DistributorRenderer;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityCrop;
import sct.culinarycraft.tile.TileEntityHydroponicDistributor;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		int distributorRenderId = RenderingRegistry.getNextAvailableRenderId();
		int cropRenderId = RenderingRegistry.getNextAvailableRenderId();
		
		DistributorRenderer distRender = new DistributorRenderer(distributorRenderId);
		CropRenderer cropRender = new CropRenderer(cropRenderId);
		
		((BlockHydroponicDistributor) CulinaryCraft.distributor).setRenderType(distributorRenderId);
		((CropBase) CulinaryCraft.crop).setRenderType(cropRenderId);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCountertop.class, new CounterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHydroponicDistributor.class, distRender);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrop.class, cropRender);
		
		RenderingRegistry.registerBlockHandler(distRender);
		RenderingRegistry.registerBlockHandler(cropRender);
	}
}
