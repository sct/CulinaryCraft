package sct.culinarycraft.crop;

import java.util.ArrayList;
import java.util.List;

import sct.culinarycraft.CulinaryCraft;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class Crop {
	public static Crop coffea = new Crop(5, "coffea", CulinaryCraft.seedCoffeaSeed.itemID, CulinaryCraft.seedCoffeaSeed.itemID);
	public static Crop blackPepper = new Crop(8, "blackpepper", CulinaryCraft.seedBlackPeppercorn.itemID, CulinaryCraft.seedBlackPeppercorn.itemID);
	public static Crop corn = new Crop(6, 3, "corn", CulinaryCraft.seedBlackPeppercorn.itemID, false, CulinaryCraft.seedBlackPeppercorn.itemID);
	
	public static List<Crop> crops;
	
	private int stages;
	private int height;
	private int cropId;
	private int seedId;
	private boolean alwaysTall;
	private String internalName;
	private Icon[][] icons;
	
	public Crop(int stages, int height, String internalName, int cropId, boolean alwaysTall, int seedId) {
		this.stages = stages;
		this.height = height;
		this.internalName = internalName;
		this.cropId = cropId;
		this.seedId = seedId;
		this.alwaysTall = alwaysTall;
		this.icons = new Icon[height][stages];
		
		if (crops == null) crops = new ArrayList<Crop>();
		
		crops.add(this);
	}
	
	public Crop(int stages, String internalName, int cropId, int seedId) {
		this(stages, 1, internalName, cropId, false, seedId);
	}
	
	public static List<Crop> values() {
		return crops;
	}
	
	public static Crop getCropFromIndex(int index) {
		return crops.get(index);
	}
	
	public static void loadTextures(IconRegister ir) {
		for (Crop crop : crops) {
			crop.loadIcons(ir);
		}
	}
	
	public int getStages() {
		return stages;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getInternalName() {
		return internalName;
	}
	
	public int getCropId() {
		return cropId;
	}
	
	public int getSeedId() {
		return seedId;
	}
	
	public boolean isAlwaysTall() {
		return alwaysTall;
	}
	
	public void loadIcons(IconRegister ir) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < stages; j++) {
				if (j / (stages / height) >= i || isAlwaysTall())
					icons[i][j] = ir.registerIcon("crop_" + getInternalName() + "_" + j + "_" + i); // ex: crop_coffea_0_0.png in blocks
			}
		}
	}
	
	public Icon getIcon(int stage) {
		return getIcon(stage, 1);
	}
	
	public Icon getIcon(int stage, int height) {
		if (stage > stages - 1) stage = stages - 1;
		height--;
		return icons[height][stage];
	}
}
