package sct.culinarycraft.tile;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.net.PacketWrapper;
import sct.culinarycraft.net.Packets;
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
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getToolPacket());
	}
	
	public void setClientTool(int itemId) {
		this.toolId = itemId;
	}
	
	public Packet getToolPacket() {
		Object[] toSend = { xCoord, yCoord, zCoord, toolId};
		return PacketWrapper.createPacket(CulinaryCraft.modNetworkChannel, Packets.ToolChange, toSend);
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
