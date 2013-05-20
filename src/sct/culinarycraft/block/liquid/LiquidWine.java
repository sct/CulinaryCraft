package sct.culinarycraft.block.liquid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.ILiquid;
import sct.culinarycraft.block.BlockFluidClassic;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;

public class LiquidWine extends BlockFluidClassic implements ILiquid {
	
	private Icon still;
	private Icon flowing;

	public LiquidWine(int id) {
		super(id, Material.water);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("wine");
		setLightOpacity(3);
		setRenderPass(1);
		setTickRate(10);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		still = ir.registerIcon("wine_still");
		flowing = ir.registerIcon("wine_flowing");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side <= 1 ? still : flowing;
	}

	@Override
	public int stillLiquidId() {
		return blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

}
