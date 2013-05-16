package sct.culinarycraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;

public class ItemMicrowave extends ItemKitchenTool {

	public ItemMicrowave(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.microwave");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		itemIcon = ir.registerIcon("microwave");
	}

}
