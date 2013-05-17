package sct.culinarycraft.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sct.culinarycraft.tile.TileEntityCountertop;

public class CountertopRecipe {
	
	private ItemStack result;
	private Item requiredTool;
	private boolean powerConsumer;
	private List<ItemStack> recipe;
	
	public CountertopRecipe(ItemStack result, Item requiredTool, List<ItemStack> recipe) {
		this(result, requiredTool, recipe, false);
	}
	
	public CountertopRecipe(ItemStack result, Item requiredTool, List<ItemStack> recipe, boolean powerConsumer) {
		this.result = result;
		this.requiredTool = requiredTool;
		this.recipe = recipe;
		this.powerConsumer = powerConsumer;
	}
	
	public List<ItemStack> getRecipe() {
		return recipe;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public Item getRequiredTool() {
		return requiredTool;
	}
	
	public boolean isPowerConsumer() {
		return powerConsumer;
	}
	
	public boolean matches(TileEntityCountertop inventory) {
		
		if (inventory.getStackInSlot(0) == null || !inventory.getStackInSlot(0).getItem().equals(getRequiredTool())) {
			return false;
		}
		
		List<ItemStack> itemList = new ArrayList<ItemStack>(recipe);
		
		for (int x = 1; x < 3; x++) {
				ItemStack is = inventory.getStackInRowAndColumn(x, 0);
				
				if (is != null) {
					Iterator<ItemStack> it = this.getRecipe().iterator();
					boolean flag = false;
					
					while (it.hasNext()) {
						ItemStack newStack = it.next();
						
						if (newStack != null && is.itemID == newStack.itemID && is.getItemDamage() == newStack.getItemDamage()) {
							flag = true;
							itemList.remove(newStack);
							break;
						}
					}
					
					if (!flag)
						return false;
				}
		}
		return itemList.isEmpty();
	}
}
