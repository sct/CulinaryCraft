package net.sctgaming.baconpancakes.tile;

import net.minecraft.item.ItemStack;
import net.sctgaming.baconpancakes.item.ItemKitchenTool;
import net.sctgaming.baconpancakes.recipe.OvenRecipe;
import net.sctgaming.baconpancakes.recipe.RecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityOven extends TileEntityMachineInventory {
	
	private int cookTime = 0;
	private OvenRecipe currentRecipe = null;
	
	public TileEntityOven() {
		super();
	}
	
	@Override
	public boolean canRotate() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (currentRecipe != null && cookTime > 0) {
			cookTime--;
			if (cookTime == 1) {
				ItemStack result = currentRecipe.getResult();
				for (int i = 0; i < 9; i++) {
					ItemStack item = this.getStackInSlot(i);
					if (item != null && item.stackSize == 1 && !(item.getItem() instanceof ItemKitchenTool))
						this.setInventorySlotContents(i, null);
					else if (item != null && item.stackSize > 1) {
						item.stackSize--;
					}
				}
				this.setInventorySlotContents(9, result);
				cookTime = 0;
				currentRecipe = null;
			}
		}
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return cookTime * par1 / 200;
	}
	
	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}
	
	public void checkRecipes() {
		OvenRecipe newRecipe = RecipeManager.getOvenRecipe(this);
		if (newRecipe != null) {
			if (currentRecipe == null || !currentRecipe.equals(newRecipe)) {
				currentRecipe = newRecipe;
				setCookTime(200);
			}
			
		} else if (getCookTime() > 0 && newRecipe == null) {
			setCookTime(0);
			currentRecipe = null;
		}
	}

	@Override
	int getInventoryWidth() {
		return 3;
	}

	@Override
	int getInventoryHeight() {
		return 3;
	}



}
