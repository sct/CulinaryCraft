package sct.culinarycraft.tile;

import java.util.HashSet;
import java.util.Set;

import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.util.Vec3Hash;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHydroponicResevoir extends TileEntity {
	
	private Vec3Hash distributor = null;
	
	public boolean isNetworked() {
		return distributor != null;
	}
	
	public Vec3Hash getDistributor() {
		return distributor;
	}
	
	public void findDistributor() {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <=1; z++) {
				if ((x == 0 || z == 0) && !(x == 0 && z == 0)) {
					TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord, zCoord + z);
					if (te != null && te instanceof TileEntityHydroponicResevoir) {
						Vec3Hash vec = ((TileEntityHydroponicResevoir) te).getDistributor();
						if (vec != null) {
							TileEntity dist = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
							if (dist != null && dist instanceof TileEntityHydroponicDistributor) {
								setDistributor(vec);
								((TileEntityHydroponicDistributor) dist).addResevoir(xCoord, yCoord, zCoord);
							}
							
							return;
						}
					} else if (te != null && te instanceof TileEntityHydroponicDistributor) {
						setDistributor(te.xCoord, te.yCoord, te.zCoord);
						((TileEntityHydroponicDistributor) te).addResevoir(xCoord, yCoord, zCoord);
						return;
					}
				}
			}
		}
	}
	
	public void verifyNetwork() {
		if (isNetworked() && !pathToDistributor(null, new Vec3Hash(xCoord, yCoord, zCoord).dotProduct(distributor), xCoord, yCoord, zCoord)) {
			TileEntity te = worldObj.getBlockTileEntity((int) distributor.xCoord, (int) distributor.yCoord, (int) distributor.zCoord);
			if (te != null && te instanceof TileEntityHydroponicDistributor) {
				((TileEntityHydroponicDistributor) te).disconnectNode(new Vec3Hash(xCoord, yCoord, zCoord));
			}
			this.disconnect();
			if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			}
			System.out.println("Resevoir @ " + xCoord + ", " + yCoord + ", " + zCoord + " lost connection to network!");
		}
	}
	
	public boolean pathToDistributor(Set<Vec3Hash> closed, double lowestDistance, int xPos, int yPos, int zPos) {
		if (closed == null) {
			closed = new HashSet<Vec3Hash>();
		}
		
		int blockId = 0;
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <=1; z++) {
				if ((x == 0 || z == 0) && !(x == 0 && z == 0)) {
					blockId = worldObj.getBlockId(xPos + x, yPos, zPos + z);
					Vec3Hash blockVec = new Vec3Hash(xPos + x, yPos, zPos + z);
					if ((blockId == CulinaryCraft.resevoir.blockID || blockId == CulinaryCraft.distributor.blockID) 
							&& !closed.contains(blockVec)) {
						TileEntity te = worldObj.getBlockTileEntity(xPos + x, yPos, zPos + z);
						if (te != null && te instanceof TileEntityHydroponicResevoir
								&& ((TileEntityHydroponicResevoir) te).isNetworked()) {
							Vec3Hash teVec = new Vec3Hash(te.xCoord, te.yCoord, te.zCoord);
							
							if (teVec.dotProduct(distributor) < lowestDistance) {
								closed.add(blockVec);
								return pathToDistributor(closed, lowestDistance, xPos + x, yPos, zPos + z);
							}
						} else if (te != null && te instanceof TileEntityHydroponicDistributor &&
								((int) distributor.xCoord == te.xCoord && (int) distributor.yCoord == te.yCoord 
								&& (int) distributor.zCoord == te.zCoord)) {
							return true;
						}
						
					}
				}
			}
		}
		
		return false;
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
