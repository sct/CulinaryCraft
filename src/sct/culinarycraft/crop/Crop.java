package sct.culinarycraft.crop;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import sct.culinarycraft.CulinaryCraft;

public class Crop {
	public static Crop coffea = new Crop(5, "coffea", new ItemStack(CulinaryCraft.crop, 1, 0));
	public static Crop blackPepper = new Crop(8, "blackpepper", new ItemStack(CulinaryCraft.crop, 1, 1));
	public static Crop corn = new Crop(6, 3, "corn", new ItemStack(CulinaryCraft.corn), false, false, 0);
	public static Crop grape = new Crop(4, 2, "grape", new ItemStack(CulinaryCraft.grape), true, true, 1);
	public static Crop onion = new Crop(4, "onion", new ItemStack(CulinaryCraft.crop, 1, 4), 2);
	
	public static List<Crop> crops;
	
	private int stages;
	private int height;
	private ItemStack crop;
	private int specialRenderer;
	private boolean alwaysTall;
	private boolean persistent;
	private String internalName;
	private Icon[][] icons;
	private Icon seedIcon;
	private Icon[] specialIcons;
	
	public Crop(int stages, int height, String internalName, ItemStack crop, boolean alwaysTall, boolean keepPlant, int specialRenderer) {
		this.stages = stages;
		this.height = height;
		this.internalName = internalName;
		this.crop = crop;
		this.specialRenderer = specialRenderer;
		this.persistent = keepPlant;
		this.alwaysTall = alwaysTall;
		this.icons = new Icon[height][stages];
		
		if (specialRenderer == 1) {
			this.specialIcons = new Icon[2];
		}
		
		if (crops == null) crops = new ArrayList<Crop>();
		
		crops.add(this);
	}
	
	public Crop(int stages, String internalName, ItemStack crop, int specialRenderer) {
		this(stages, 1, internalName, crop, false, false, specialRenderer);
	}
	
	public Crop(int stages, String internalName, ItemStack crop) {
		this(stages, 1, internalName, crop, false, false, 0);
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
	
	public ItemStack getCrop() {
		return crop;
	}
	
	public boolean isAlwaysTall() {
		return alwaysTall;
	}
	
	public boolean isPersistent() {
		return persistent;
	}
	
	public int getSpecialRenderer() {
		return specialRenderer;
	}
	
	public void loadIcons(IconRegister ir) {
		seedIcon = ir.registerIcon("crop_" + getInternalName() + "_seed");
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < stages; j++) {
				if (j / (stages / height) >= i || isAlwaysTall())
					icons[i][j] = ir.registerIcon("crop_" + getInternalName() + "_" + j + "_" + i); // ex: crop_coffea_0_0.png in blocks
			}
		}
		
		if (getSpecialRenderer() == 1) {
			specialIcons[0] = ir.registerIcon("crop_grape_tree_0");
			specialIcons[1] = ir.registerIcon("crop_grape_tree_1");
		}
	}
	
	public Icon getSeedIcon() {
		return seedIcon;
	}
	
	/**
	 * This should only be called by the renderer for crops it knows how to handle. Otherwise, explosions.
	 * 
	 * @param index
	 * @return
	 */
	public Icon getSpecialIcon(int index) {
		return specialIcons[index];
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
