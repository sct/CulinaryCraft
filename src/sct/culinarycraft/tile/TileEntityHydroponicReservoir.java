package sct.culinarycraft.tile;

import java.util.HashSet;
import java.util.Set;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.util.Vec3Hash;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHydroponicReservoir extends TileEntity {
	
	private Vec3Hash distributor = null;
	
	public boolean isNetworked() {
		return distributor != null;
	}
	
	public Vec3Hash getDistributor() {
		return distributor;
	}
	
	public int getGrowthChances() {
		return 1;
	}
	
	public void findDistributor() {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <=1; z++) {
				if ((x == 0 || z == 0) && !(x == 0 && z == 0)) {
					TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
					if (te != null && te instanceof TileEntityHydroponicReservoir) {
						Vec3Hash vec = ((TileEntityHydroponicReservoir) te).getDistributor();
						if (vec != null) {
							TileEntity dist = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
							if (dist != null && dist instanceof TileEntityHydroponicDistributor) {
								setDistributor(vec);
								((TileEntityHydroponicDistributor) dist).addResevoirWithScan(xCoord, yCoord, zCoord);
							}
							
							return;
						}
					} else if (te != null && te instanceof TileEntityHydroponicDistributor) {
						setDistributor(te.xCoord, te.yCoord, te.zCoord);
						((TileEntityHydroponicDistributor) te).addResevoirWithScan(xCoord, yCoord, zCoord);
						return;
					}
				}
			}
		}
	}
	
	public void verifyNetwork() {
		if (isNetworked()) {
			pathToDistributor();
		}
	}
	
	public boolean pathToDistributor() {
		int blockId = 0;
		Set<Vec3Hash> open = new HashSet<Vec3Hash>();
		Set<Vec3Hash> closed = new HashSet<Vec3Hash>();
		Vec3Hash curNode = null;
		
		open.add(new Vec3Hash(xCoord, yCoord, zCoord));
		
		while (!open.contains(distributor)) {
			curNode = null;
			
			if (open.isEmpty()) {
				System.out.println("No distributor found. Removing from network");
				
				/* Now that we know it cant find the dist, we have to set any resevoirs in the closed list to disconnected*/
				TileEntity te = worldObj.getBlockTileEntity((int) distributor.xCoord, (int) distributor.yCoord, (int) distributor.zCoord);
				for (Vec3Hash vec : closed) {
					TileEntity resevoir = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
					if (resevoir != null && resevoir instanceof TileEntityHydroponicReservoir) {
						if (te != null && te instanceof TileEntityHydroponicDistributor) {
							((TileEntityHydroponicDistributor) te).disconnectNode(vec);
						}
						
						((TileEntityHydroponicReservoir) resevoir).disconnect();
						
						if (worldObj.getBlockMetadata((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord) == 1) {
							worldObj.setBlockMetadataWithNotify((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord, 0, 2);
						}
					}
				}
				
				return false;
			}
			
			for (Vec3Hash node : open){
				if (curNode == null || node.dotProduct(distributor) < curNode.dotProduct(distributor)) {
					curNode = node;
				}
			}
			
			closed.add(curNode);
			open.remove(curNode);
			
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <=1; z++) {
					if ((x == 0 || z == 0) && !(x == 0 && z == 0)) {
						blockId = worldObj.getBlockId((int) curNode.xCoord + x, (int) curNode.yCoord, (int) curNode.zCoord + z);
						Vec3Hash blockVec = new Vec3Hash((int) curNode.xCoord + x, (int) curNode.yCoord, (int) curNode.zCoord + z);
						if ((blockId == CulinaryCraft.reservoir.blockID || blockId == CulinaryCraft.distributor.blockID) 
								&& !closed.contains(blockVec)) {
							if (!open.contains(blockVec)) {
								open.add(blockVec);
							}
						}
					}
				}
			}
		}
		
		System.out.println("found dist!");
		return true;
	}
	
	public void setDistributor(int x, int y, int z) {
		this.distributor = new Vec3Hash(x, y, z);
		System.out.println("Distributor registered for resevoir @ " + xCoord + ", " + yCoord + ", " + zCoord);
	}
	
	public void setDistributor(Vec3Hash vec) {
		this.distributor = vec;
		System.out.println("Distributor registered for resevoir @ " + xCoord + ", " + yCoord + ", " + zCoord);
	}
	
	public void disconnect() {
		this.distributor = null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		if (tagCompound.getBoolean("networked")) {
			setDistributor(tagCompound.getInteger("distX"), tagCompound.getInteger("distY"), tagCompound.getInteger("distZ"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setBoolean("networked", isNetworked());
		
		if (distributor != null) {
			tagCompound.setInteger("distX", (int)distributor.xCoord);
			tagCompound.setInteger("distY", (int)distributor.yCoord);
			tagCompound.setInteger("distZ", (int)distributor.zCoord);
		}
	}

}
