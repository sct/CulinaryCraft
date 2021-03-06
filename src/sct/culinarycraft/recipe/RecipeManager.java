package sct.culinarycraft.recipe;

import java.util.ArrayList;
import java.util.List;

import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityOven;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeManager {
	public static List<OvenRecipe> ovenRecipes = new ArrayList<OvenRecipe>();
	public static List<CountertopRecipe> counterRecipes = new ArrayList<CountertopRecipe>();
	
	public static List<OvenRecipe> getOvenRecipes() {
		return ovenRecipes;
	}
	
	public static List<CountertopRecipe> getCountertopRecipes() {
		return counterRecipes;
	}
	
	public static List<ItemStack> getRecipeList(Object... itemStacks) {
		List<ItemStack> itemList = new ArrayList<ItemStack>(0);
		Object[] objArray = itemStacks;
		
		for (int x = 0; x < itemStacks.length; x++) {
			
			if (objArray[x] instanceof ItemStack) {
				itemList.add((ItemStack) objArray[x]);
			} else if (objArray[x] instanceof Item) {
				itemList.add(new ItemStack((Item) objArray[x]));
			} else {
				itemList.add(null);
			}
		}
		
		return itemList;
	}
	
	public static void addOvenRecipe(ItemStack result, Object... itemStacks) {
		List<ItemStack> objList = getRecipeList(itemStacks);
		ovenRecipes.add(new OvenRecipe(result, objList));
	}
	
	public static void addShapelessOvenRecipe(ItemStack result, Object... itemStacks) {
		List<ItemStack> objList = getRecipeList(itemStacks);
		ovenRecipes.add(new OvenRecipe(result, objList, true));
	}
	
	public static void addCountertopRecipe(ItemStack result, Item requiredTool, Object... itemStacks) {
		List<ItemStack> objList = getRecipeList(itemStacks);
		counterRecipes.add(new CountertopRecipe(result, requiredTool, objList));
	}
	
	public static OvenRecipe getOvenRecipe(TileEntityOven inventory) {
		for (OvenRecipe recipe : ovenRecipes) {
			if (recipe.matches(inventory))
				return recipe;
		}
		return null;
	}
	
	public static CountertopRecipe getCountertopRecipe(TileEntityCountertop inventory) {
		for (CountertopRecipe recipe : counterRecipes) {
			if (recipe.matches(inventory))
				return recipe;
		}
		return null;
	}
}
