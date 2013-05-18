package sct.culinarycraft.tile;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import sct.culinarycraft.crop.Crop;

public class TileEntityCrop extends TileEntity {
	private Crop crop = null;
	
	public TileEntityCrop() {
		this.crop = Crop.coffea;
	}
	
	public Crop getCrop() {
		return crop;
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
		Packet132TileEntityData packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, data);
		return packet;
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		setClientCrop(pkt.customParam1.getInteger("cropindex"));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		setCropByIndex(tagCompound.getInteger("cropindex"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("cropindex", Crop.values().indexOf(crop));
	}
}
