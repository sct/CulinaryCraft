package sct.culinarycraft.util;

import sct.culinarycraft.item.ItemHandMixer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class CulinaryCraftUtils {
	
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
