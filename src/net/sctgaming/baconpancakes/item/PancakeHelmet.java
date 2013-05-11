package net.sctgaming.baconpancakes.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.sctgaming.baconpancakes.gui.BaconPancakesCreativeTab;
import net.sctgaming.baconpancakes.model.ModelPancakeHelmet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PancakeHelmet extends ItemArmor {
	
	private final static ModelPancakeHelmet mph = new ModelPancakeHelmet();

	public PancakeHelmet(int id) {
		super(id, EnumArmorMaterial.DIAMOND, 3, 0);
		setCreativeTab(BaconPancakesCreativeTab.tab);
		setUnlocalizedName("sctpancakehelmet");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("pancakeHelmet");
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		return "/textures/maps/PancakeHelmetMap.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLiving entityLiving,
			ItemStack itemStack, int armorSlot) {
		return mph;
	}

}
