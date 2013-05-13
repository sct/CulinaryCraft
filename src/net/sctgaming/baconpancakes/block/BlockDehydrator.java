package net.sctgaming.baconpancakes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDehydrator extends Block {
	
	private Icon[] icons = new Icon[6];

	public BlockDehydrator(int id) {
		super(id, Material.rock);
		setHardness(0.5F);
		setStepSound(soundMetalFootstep);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sct.dehydrator");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		icons[0] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[1] = ir.registerIcon("tile.sct.dehydrator.idle.top");
		icons[2] = ir.registerIcon("tile.sct.dehydrator.idle.front");
		icons[3] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[4] = ir.registerIcon("tile.sct.dehydrator.idle.side");
		icons[5] = ir.registerIcon("tile.sct.dehydrator.idle.side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side > 1)
		{
			side += 2;
			if (side > 5)
			{
				side -= 4;
			}
		}
		return icons[side];
	}

}
