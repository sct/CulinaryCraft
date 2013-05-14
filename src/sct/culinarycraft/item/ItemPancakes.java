package sct.culinarycraft.item;

import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import sct.culinarycraft.renderer.PancakeRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPancakes extends ItemFood {

	public ItemPancakes(int id) {
		super(id, 4, true);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("sctpancakes");
		MinecraftForgeClient.registerItemRenderer(this.itemID, new PancakeRenderer());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("pancakes");
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemStack, int armorSlot) {
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}
	
	
	
	

}
