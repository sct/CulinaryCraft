package sct.culinarycraft.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.core.ITankContainerBucketable;

public class TileEntityDehydrator extends TileEntityMachinePowered implements ITankContainerBucketable {
	
	private LiquidTank tank;
	private int tick = 0;

	public TileEntityDehydrator() {
		super(4);
		tank = new LiquidTank(4 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (isPowered() && tank.getLiquid() != null && tank.getLiquid().amount > 10
				&& (getStackInSlot(0) == null || getStackInSlot(0).stackSize < 64)) {
			setWorking(true);
			tank.drain(5, true);
			tick++;
			if (tick >= 1200) {
				ItemStack stack = new ItemStack(CulinaryCraft.salt);
				if (getStackInSlot(0) != null && getStackInSlot(0).itemID == CulinaryCraft.salt.itemID) {
					getStackInSlot(0).stackSize++;
				} else {
					setInventorySlotContents(0, stack);
				}
				tick = 0;
			}
		} else {
			setWorking(false);
		}
	}
	
	@Override
	public boolean canRotate() {
		return true;
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getEnergyStoredMax() {
		return 500;
	}

	@Override
	public int getIdleTicksMax() {
		return 20;
	}
	
	public int getTick() {
		return tick;
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
	public String getInvName() {
		return "Dehydrator";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		LiquidStack l = LiquidStack.loadLiquidStackFromNBT(tagCompound);
		if (l != null) {
			tank.setLiquid(l);
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		LiquidStack l = tank.getLiquid();
		if (l != null) {
			l.writeToNBT(tagCompound);
		}
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
