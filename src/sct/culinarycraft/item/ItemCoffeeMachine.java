package sct.culinarycraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;

public class ItemCoffeeMachine extends ItemKitchenTool {

	public ItemCoffeeMachine(int id) {
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.coffeemachine");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		itemIcon = ir.registerIcon("coffeeMachine");
	}

}
