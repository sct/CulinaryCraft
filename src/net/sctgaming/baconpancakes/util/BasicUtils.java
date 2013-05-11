package net.sctgaming.baconpancakes.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.sctgaming.baconpancakes.item.ItemHandMixer;

public class BasicUtils {
	
	public static boolean isHoldingMixer(EntityPlayer player)
	{
		if (player.inventory.getCurrentItem() == null) {
			return false;
		}
		Item currentItem = Item.itemsList[player.inventory.getCurrentItem().itemID];
		if (currentItem != null && currentItem instanceof ItemHandMixer) {
			return true;
		}
		return false;
	}
}
