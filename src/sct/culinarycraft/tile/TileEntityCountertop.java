package sct.culinarycraft.tile;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityCountertop extends TileEntityMachinePowered {
	
	private int toolId = 0;

	public TileEntityCountertop() {
		super(3);
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
	
	@Override
	public boolean canRotate() {
		return true;
	}

	@Override
	public int getSizeInventory() {
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
		// TODO Auto-generated method stub
		super.readFromNBT(tagCompound);
		setTool(tagCompound.getInteger("toolId"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("toolId", toolId);
	}

}
