package sct.culinarycraft.tile;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.core.ITankContainerBucketable;
import sct.culinarycraft.util.Vec3Hash;

public class TileEntityHydroponicDistributor extends TileEntityMachine implements ITankContainerBucketable {
	
	private LiquidTank tank;
	private int tick = 0;
	private boolean activeState = true;
	
	private Set<Vec3Hash> connectedReservoirs = new HashSet<Vec3Hash>();
	
	public TileEntityHydroponicDistributor() {
		tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (tank.getLiquid() != null && tank.getLiquid().amount > 2 * connectedReservoirs.size()) {
			activeState = true;
			tick++;
			
			if (tick >= 40) {
				tank.drain(1 * connectedReservoirs.size(), true);
				tick = 0;
			}
			
			
			for (Vec3Hash vec : connectedReservoirs) {
				TileEntity te = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
				if (te != null && te instanceof TileEntityHydroponicReservoir 
						&& worldObj.getBlockMetadata((int) xCoord, (int) yCoord, (int) zCoord) != 1) {
					worldObj.setBlockMetadataWithNotify((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord, 1, 2);
				}
			}
		} else if (activeState == true) {
			activeState = false;
			for (Vec3Hash vec : connectedReservoirs) {
				TileEntity te = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
				if (te != null && te instanceof TileEntityHydroponicReservoir) {
					worldObj.setBlockMetadataWithNotify((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord, 0, 2);
				}
			}
		}
	}

	public void findConnectedResevoirs(int x, int y, int z) {
		Set<Vec3Hash> resevoirs = new HashSet<Vec3Hash>();
		resevoirs = getAdjacentResevoirBlocks(resevoirs, x, y, z);
		
		for (Vec3Hash vec : connectedReservoirs) {
			if (!resevoirs.contains(vec)) {
				TileEntity te = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
				if (te != null && te instanceof TileEntityHydroponicReservoir) {
					((TileEntityHydroponicReservoir) te).disconnect();
					worldObj.setBlockMetadataWithNotify((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord, 0, 2);
				}
			}
		}
		
		connectedReservoirs = resevoirs;
	}
	
	public Set<Vec3Hash> getAdjacentResevoirBlocks(Set<Vec3Hash> resevoirs, int xPos, int yPos, int zPos) {
		int blockId = 0;
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <=1; z++) {
				if ((x == 0 || z == 0)  && !(x == 0 && z == 0)) {
					blockId = worldObj.getBlockId(xPos + x, yPos, zPos + z);
					Vec3Hash blockVec = new Vec3Hash(xPos + x, yPos, zPos + z);
					if (blockId == CulinaryCraft.reservoir.blockID && !resevoirs.contains(blockVec)) {
						TileEntity te = worldObj.getBlockTileEntity(xPos + x, yPos, zPos + z);
						if (te != null && te instanceof TileEntityHydroponicReservoir 
								&& (!((TileEntityHydroponicReservoir) te).isNetworked() 
										|| ((TileEntityHydroponicReservoir) te).getDistributor().equals(new Vec3Hash(xCoord, yCoord, zCoord)))) {
							resevoirs.add(blockVec);
							((TileEntityHydroponicReservoir) te).setDistributor(xCoord, yCoord, zCoord);
							getAdjacentResevoirBlocks(resevoirs, xPos + x, yPos, zPos + z);
						}
						
					}
				}
			}
		}
		
		return resevoirs;
	}
	
	public void addResevoir(int x, int y, int z) {
		addResevoir(new Vec3Hash(x, y, z));
	}
	
	public void addResevoirWithScan(int x, int y, int z) {
		addResevoir(new Vec3Hash(x, y, z));
		findConnectedResevoirs(xCoord, yCoord, zCoord);
	}
	
	public void addResevoir(Vec3Hash vec) {
		connectedReservoirs.add(vec);
	}
	
	public void disconnectNode(Vec3Hash vec) {
		connectedReservoirs.remove(vec);
	}
	
	public void disconnectNetwork() {
		for (Vec3Hash vec : connectedReservoirs) {
			int blockId = worldObj.getBlockId((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
			if (blockId == CulinaryCraft.reservoir.blockID) {
				TileEntity te = worldObj.getBlockTileEntity((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord);
				if (te != null && te instanceof TileEntityHydroponicReservoir) {
					worldObj.setBlockMetadataWithNotify((int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord, 0, 2);
					((TileEntityHydroponicReservoir) te).disconnect();
				}
			}
		}
		connectedReservoirs.clear();
	}
	
	@Override
	public int getIdleTicksMax() {
		return 0;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		if (resource == null || resource.itemID != LiquidDictionary.getCanonicalLiquid("Water").itemID) {
			return 0;
		} else {
			return tank.fill(resource, doFill);
		}
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return fill(ForgeDirection.UNKNOWN, resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { tank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if (type != null && type.itemID == LiquidDictionary.getCanonicalLiquid("Water").itemID) {
			return tank;
		}
		return null;
	}
	
	public int getLiquidAmount() {
		if (getTank(ForgeDirection.UNKNOWN, LiquidDictionary.getCanonicalLiquid("Water")).getLiquid() != null) {
			return getTank(ForgeDirection.UNKNOWN, LiquidDictionary.getCanonicalLiquid("Water")).getLiquid().amount;
		}
		return 0;
	}
	
	public LiquidTank getTank() {
		return tank;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		LiquidStack l = LiquidStack.loadLiquidStackFromNBT(tagCompound);
		if (l != null) {
			tank.setLiquid(l);
		}
		
		NBTTagList tagList = tagCompound.getTagList("resevoirs");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			int[] coords = tag.getIntArray("vector");
			addResevoir(coords[0], coords[1], coords[2]);
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		LiquidStack l = tank.getLiquid();
		if (l != null) {
			l.writeToNBT(tagCompound);
		}
		
		NBTTagList tagList = new NBTTagList();
		for (Vec3Hash vec : connectedReservoirs) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setIntArray("vector", new int[] {(int) vec.xCoord, (int) vec.yCoord, (int) vec.zCoord});
			tagList.appendTag(tag);
		}
		tagCompound.setTag("resevoirs", tagList);
	}

	@Override
	public boolean allowBucketFill() {
		return true;
	}

	@Override
	public boolean allowBucketDrain() {
		return false;
	}

}
