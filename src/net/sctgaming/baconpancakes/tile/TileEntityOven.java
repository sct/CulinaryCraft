package net.sctgaming.baconpancakes.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityOven extends TileEntity implements IInventory {
	
	private ItemStack[] inv;
	private ForgeDirection forwardDirection;
	
	public TileEntityOven() {
		inv = new ItemStack[9];
		forwardDirection = ForgeDirection.NORTH;
	}
	
	public ForgeDirection getDirectionFacing() {
		return forwardDirection;
	}
	
	public boolean canRotate() {
		return true;
	}
	
	public void rotate() {
		if (!worldObj.isRemote) {
			if (forwardDirection == ForgeDirection.NORTH) {
				forwardDirection = ForgeDirection.EAST;
			} else if (forwardDirection == ForgeDirection.EAST) {
				forwardDirection = ForgeDirection.SOUTH;
			} else if (forwardDirection == ForgeDirection.SOUTH) {
				forwardDirection = ForgeDirection.WEST;
			} else if (forwardDirection == ForgeDirection.WEST){
				forwardDirection = ForgeDirection.NORTH;
			} else {
				forwardDirection = ForgeDirection.NORTH;
			}
			
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
		}
	}
	
	public void rotateDirectlyTo(int rotation) {
		forwardDirection = ForgeDirection.getOrientation(rotation);
		if (worldObj != null) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public int getRotatedSide(int side) {
		if (side < 2) {
			return side;
		} else if (forwardDirection == ForgeDirection.EAST){
			return addToSide(side, 1);
		} else if (forwardDirection == ForgeDirection.SOUTH) {
			return addToSide(side, 2);
		} else if (forwardDirection == ForgeDirection.WEST) {
			return addToSide(side, 3);
		}
		return side;
	}
	
	private int addToSide(int side, int shift) {
		int shiftsRemaining = shift;
		int out = side;
		
		while (shiftsRemaining > 0) {
			if (out == 2) out = 4;
			else if (out == 4) out = 3;
			else if (out == 3) out = 5;
			else if (out == 5) out = 2;
			shiftsRemaining--;
		}
		
		return out;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		rotateDirectlyTo(pkt.customParam1.getInteger("rotation"));
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("rotation", getDirectionFacing().ordinal());
		Packet132TileEntityData packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, data);
		return packet;
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		inv[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "sct.tile.oven";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		int rotation = tagCompound.getInteger("rotation");
		rotateDirectlyTo(rotation);
		
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("rotation", getDirectionFacing().ordinal());
		
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

}
