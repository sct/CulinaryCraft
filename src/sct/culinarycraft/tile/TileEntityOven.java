package sct.culinarycraft.tile;

import sct.culinarycraft.item.ItemKitchenTool;
import sct.culinarycraft.recipe.OvenRecipe;
import sct.culinarycraft.recipe.RecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityOven extends TileEntityMachinePowered {
	
	private int cookTime = 0;
	private OvenRecipe currentRecipe = null;
	
	public TileEntityOven() {
		super(10);
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
		
		if (isPowered() && currentRecipe != null && cookTime > 0
				&& (getStackInSlot(9) == null || this.getStackInSlot(9).getItem().equals(currentRecipe.getResult().getItem()))) {
			cookTime--;
			if (cookTime == 1) {
				ItemStack result = currentRecipe.getResult().copy();
				for (int i = 0; i < 9; i++) {
					ItemStack item = this.getStackInSlot(i);
					if (item != null && item.stackSize == 1 && !(item.getItem() instanceof ItemKitchenTool))
						this.setInventorySlotContents(i, null);
					else if (item != null && item.stackSize > 1) {
						item.stackSize--;
					}
				}
				if (getStackInSlot(9) != null && getStackInSlot(9).getItem().equals(currentRecipe.getResult().getItem())) {
					getStackInSlot(9).stackSize++;
				} else {
					this.setInventorySlotContents(9, result);
				}
				cookTime = 0;
				currentRecipe = null;
				setWorking(false);
				checkRecipes();
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
				setWorking(true);
			}
			
		} else if (getCookTime() > 0 && newRecipe == null) {
			setCookTime(0);
			currentRecipe = null;
			setWorking(false);
		}
	}

	@Override
	public int getInventoryWidth() {
		return 3;
	}

	@Override
	public int getInventoryHeight() {
		return 3;
	}

	@Override
	public int getEnergyStoredMax() {
		return 500;
	}
	
	@Override
	public int getIdleTicksMax() {
		return 20;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		cookTime = tagCompound.getInteger("cooktime");
		
		int recipeIndex = tagCompound.getInteger("recipe");
		if (recipeIndex != -1 && cookTime != 0) {
			currentRecipe = RecipeManager.ovenRecipes.get(recipeIndex);
			setWorking(true);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("cooktime", cookTime);
		if (currentRecipe != null)
			tagCompound.setInteger("recipeIndex", RecipeManager.ovenRecipes.indexOf(currentRecipe));
	}

}
