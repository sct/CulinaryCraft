package sct.culinarycraft.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIngredient extends Item {
	
	int metaMax = 0;

	public ItemIngredient(int id) {
		super(id);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
	}
	
	protected void setMetaMax(int metaMax) {
		this.metaMax = metaMax;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		this.itemIcon = ir.registerIcon(getUnlocalizedName());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs tabs,
			List subtypes) {
		for(int meta = 0; meta <= metaMax; meta++)
		{
			subtypes.add(new ItemStack(itemId, 1, meta));
		}
	}

}
