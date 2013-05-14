package sct.culinarycraft;

import sct.culinarycraft.renderer.CounterRenderer;
import sct.culinarycraft.tile.TileEntityCountertop;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCountertop.class, new CounterRenderer());
	}
}
