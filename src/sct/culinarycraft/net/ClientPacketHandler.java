package sct.culinarycraft.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import sct.culinarycraft.tile.TileEntityCountertop;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int packetType = PacketWrapper.readPacketID(data);
		
		if (packetType == Packets.ToolChange) {
			Class[] decodeAs = { Integer.class, Integer.class, Integer.class, Integer.class };
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity((Integer)packetReadout[0], (Integer)packetReadout[1], (Integer)packetReadout[2]);
			if (te instanceof TileEntityCountertop) {
				((TileEntityCountertop)te).setClientTool((Integer)packetReadout[3]);
			}
		}
		
	}

}
