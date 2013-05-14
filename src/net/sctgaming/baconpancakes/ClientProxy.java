package net.sctgaming.baconpancakes;

import net.sctgaming.baconpancakes.renderer.CounterRenderer;
import net.sctgaming.baconpancakes.tile.TileEntityCountertop;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCountertop.class, new CounterRenderer());
	}
}
