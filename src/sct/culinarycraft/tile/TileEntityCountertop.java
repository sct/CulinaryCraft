package sct.culinarycraft.tile;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import sct.culinarycraft.item.ItemKitchenTool;
import sct.culinarycraft.recipe.CountertopRecipe;
import sct.culinarycraft.recipe.RecipeManager;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCountertop extends TileEntityMachinePowered {
	
	private int toolId = 0;
	private int cookTime = 0;
	private CountertopRecipe currentRecipe = null;
	
	public TileEntityCountertop() {
		super(3);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (currentRecipe != null && (isPowered() || !currentRecipe.isPowerConsumer()) && cookTime > 0) {
			cookTime--;
			if (cookTime == 1) {
				ItemStack result = currentRecipe.getResult().copy();
				for (int i = 1; i < 3; i++) {
					ItemStack item = this.getStackInSlot(i);
					if (item != null && item.stackSize == 1 && !(item.getItem() instanceof ItemKitchenTool))
						this.setInventorySlotContents(i, null);
					else if (item != null && item.stackSize > 1) {
						item.stackSize--;
					}
				}
				this.setInventorySlotContents(3, result);
				cookTime = 0;
				currentRecipe = null;
				setWorking(false);
			}
		}
	}
	
	public Item getTool() {
		return Item.itemsList[toolId];
	}
	
	public void setTool(int itemId) {
		toolId = itemId;
		if (worldObj != null)
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
	}
	
	public void setClientTool(int itemId) {
		this.toolId = itemId;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		rotateDirectlyTo(pkt.customParam1.getInteger("rotation"));
		setClientWorking(pkt.customParam1.getBoolean("working"));
		setClientTool(pkt.customParam1.getInteger("toolId"));
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("rotation", getDirectionFacing().ordinal());
		data.setBoolean("working", isWorking());
		data.setInteger("toolId", toolId);
		Packet132TileEntityData packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, data);
		return packet;
	}
	
	public void checkRecipes() {
		CountertopRecipe newRecipe = RecipeManager.getCountertopRecipe(this);
		if (newRecipe != null) {
			if (currentRecipe == null || !currentRecipe.equals(newRecipe)) {
				currentRecipe = newRecipe;
				setCookTime(200);
				if (currentRecipe.isPowerConsumer()) setWorking(true);
			}
			
		} else if (getCookTime() > 0 && newRecipe == null) {
			setCookTime(0);
			currentRecipe = null;
			setWorking(false);
		}
	}
	
	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}
	
	public int getCookTime() {
		return cookTime;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return cookTime * par1 / 200;
	}
	
	@Override
	public boolean canRotate() {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}
	
	@Override
	public int getInventoryWidth() {
		return 4;
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
		setTool(tagCompound.getInteger("toolId"));
		cookTime = tagCompound.getInteger("cooktime");
		
		int recipeIndex = tagCompound.getInteger("recipe");
		if (recipeIndex != -1 && cookTime != 0) {
			currentRecipe = RecipeManager.counterRecipes.get(recipeIndex);
			setWorking(true);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("toolId", toolId);
		tagCompound.setInteger("recipe", RecipeManager.counterRecipes.indexOf(currentRecipe));
		tagCompound.setInteger("cooktime", cookTime);
	}

}
