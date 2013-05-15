package sct.culinarycraft.util;

import net.minecraft.util.Vec3;

public class Vec3Hash extends Vec3 {

	public Vec3Hash(double par2, double par4,
			double par6) {
		super(fakePool, par2, par4, par6);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vec3Hash) {
			int x = (int) ((Vec3Hash) obj).xCoord;
			int y = (int) ((Vec3Hash) obj).yCoord;
			int z = (int) ((Vec3Hash) obj).zCoord;
			
			return (int) xCoord == x && (int)yCoord == y && (int)zCoord == z;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashcode = 1;
		hashcode = hashcode * 17 + (int) Math.round(xCoord);
		hashcode = hashcode * 31 + (int) Math.round(yCoord);
		hashcode = hashcode * 13 + (int) Math.round(zCoord);
		return hashcode;
	}

}
