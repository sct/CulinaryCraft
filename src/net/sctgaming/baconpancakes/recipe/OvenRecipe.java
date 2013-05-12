package net.sctgaming.baconpancakes.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.sctgaming.baconpancakes.tile.TileEntityOven;

public class OvenRecipe {
	
	private ItemStack result;
	private List<ItemStack> recipe;
	private boolean shapeless;
	
	public OvenRecipe(ItemStack result, List<ItemStack> recipe) {
		this(result, recipe, false);
	}
	
	public OvenRecipe(ItemStack result, List<ItemStack> recipe, boolean shapeless) {
		this.result = result;
		this.recipe = recipe;
		this.shapeless = shapeless;
	}
	
	public List<ItemStack> getRecipe() {
		return recipe;
	}
	
	public ItemStack getResult() {
		return result;
	}
	
	public boolean isShapeless() {
		return shapeless;
	}
	
	public boolean matches(TileEntityOven inventory) {
		if (isShapeless()) {
			List<ItemStack> itemList = new ArrayList<ItemStack>(recipe);
			
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					ItemStack is = inventory.getStackInRowAndColumn(x, y);
					
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
			}
			return itemList.isEmpty();
		}
		
		return false;
	}
}
