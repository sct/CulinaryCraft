package sct.culinarycraft.tile;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.block.CropBase;
import sct.culinarycraft.crop.Crop;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityCrop extends TileEntity {
	private Crop crop = null;
	private int height = 1;
	
	public TileEntityCrop() {
		this.crop = Crop.coffea;
	}
	
	public ItemStack getSeedStack() {
		return new ItemStack(CulinaryCraft.crop,1,Crop.values().indexOf(getCrop()));
	}
	
	public Crop getCrop() {
		return crop;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void destroyStalk() {
		int i = 1;
		while (Block.blocksList[worldObj.getBlockId(xCoord, yCoord - i, zCoord)] instanceof CropBase) {
			worldObj.setBlockToAir(xCoord, yCoord - i, zCoord);
			worldObj.removeBlockTileEntity(xCoord, yCoord - i, zCoord);
			++i;
		}
	}
	
	public Icon getIcon(int meta) {
		return getCrop().getIcon(meta, getHeight());
	}
	
	public void setHeight(int height) {
		this.height = height;
		if (worldObj != null)
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
	}
	
	public void setClientHeight(int height) {
		this.height = height;
	}
	
	public void setCrop(Crop crop) {
		this.crop = crop;
		if (worldObj != null)
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
	}
	
	public void setClientCrop(int index) {
		this.crop = Crop.getCropFromIndex(index);
	}
	
	public void setCropByIndex(int index) {
		setCrop(Crop.getCropFromIndex(index));
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("cropindex", Crop.values().indexOf(crop));
		data.setInteger("height", getHeight());
		Packet132TileEntityData packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, data);
		return packet;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		setClientCrop(pkt.customParam1.getInteger("cropindex"));
		setClientHeight(pkt.customParam1.getInteger("height"));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		setCropByIndex(tagCompound.getInteger("cropindex"));
		int height = tagCompound.getInteger("height");
		if (height != -1) setHeight(height);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("cropindex", Crop.values().indexOf(crop));
		tagCompound.setInteger("height", getHeight());
	}
}
