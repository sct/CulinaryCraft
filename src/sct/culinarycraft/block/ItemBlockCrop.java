package sct.culinarycraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockCrop extends ItemBlock {

	protected String[] names;

	public ItemBlockCrop(int id) {
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
		setNames(new String[] { "coffea", "blackpepper", "corn" });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage) {
		return ((CropBase) Block.blocksList[getBlockID()]).getSeedIcon(damage);
	}

	protected void setNames(String[] names) {
		this.names = names;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "." + names[Math.min(stack.getItemDamage(), names.length - 1)];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes) {
		for (int i = 0; i < names.length; i++) {
			subTypes.add(new ItemStack(itemId, 1, i));
		}
	}

}
