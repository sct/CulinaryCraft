package sct.culinarycraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;

public class ItemCuttingBoard extends ItemKitchenTool {

	public ItemCuttingBoard(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("sct.cuttinboard");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("cuttingBoard");
	}

}
