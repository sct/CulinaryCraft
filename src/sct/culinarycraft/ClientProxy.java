package sct.culinarycraft;

import sct.culinarycraft.block.BlockHydroponicDistributor;
import sct.culinarycraft.renderer.CounterRenderer;
import sct.culinarycraft.renderer.DistributorRenderer;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityHydroponicDistributor;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		int distributorRenderId = RenderingRegistry.getNextAvailableRenderId();
		
		DistributorRenderer distRender = new DistributorRenderer(distributorRenderId);
		
		((BlockHydroponicDistributor) CulinaryCraft.distributor).setRenderType(distributorRenderId);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCountertop.class, new CounterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHydroponicDistributor.class, distRender);
		
		RenderingRegistry.registerBlockHandler(distRender);
	}
}
