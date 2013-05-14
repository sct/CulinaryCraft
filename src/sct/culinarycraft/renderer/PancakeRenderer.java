package sct.culinarycraft.renderer;

import sct.culinarycraft.item.ItemPancakes;
import sct.culinarycraft.model.ModelPancakeHelmet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class PancakeRenderer implements IItemRenderer {

	private static final ModelPancakeHelmet mph = new ModelPancakeHelmet();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return item.getItem() instanceof ItemPancakes && type.equals(ItemRenderType.EQUIPPED);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (helper) {
			case ENTITY_ROTATION:
				return true;
			case ENTITY_BOBBING:
				return true;
			case EQUIPPED_BLOCK:
				return true;
			default:
				return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Entity player = null;
		for (Object obj : data) {
			if (obj instanceof EntityLiving) {
				player = (EntityLiving) obj;
			}
		}
		
		if (player != null) {
			mph.render(player, 0, 0, 0, 0, 0, 0);
		}
		
	}

}
