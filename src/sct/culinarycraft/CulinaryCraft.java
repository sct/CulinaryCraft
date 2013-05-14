package sct.culinarycraft;

import sct.culinarycraft.block.BlockCountertop;
import sct.culinarycraft.block.BlockDehydrator;
import sct.culinarycraft.block.BlockKitchenTile;
import sct.culinarycraft.block.BlockOven;
import sct.culinarycraft.block.crop.CropBlackPepper;
import sct.culinarycraft.gui.GuiHandler;
import sct.culinarycraft.item.ItemBacon;
import sct.culinarycraft.item.ItemCookedEgg;
import sct.culinarycraft.item.ItemFryingPan;
import sct.culinarycraft.item.ItemHandMixer;
import sct.culinarycraft.item.ItemPancakeHelmet;
import sct.culinarycraft.item.ItemPancakes;
import sct.culinarycraft.item.ItemRawBacon;
import sct.culinarycraft.item.ItemSalt;
import sct.culinarycraft.item.seed.ItemBlackPeppercorn;
import sct.culinarycraft.recipe.RecipeManager;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityDehydrator;
import sct.culinarycraft.tile.TileEntityOven;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "CulinaryCraft", name = "Culinary Craft", useMetadata = true, version = CulinaryCraft.version)
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class CulinaryCraft {
	
	@Instance("CulinaryCraft")
	public static CulinaryCraft instance;
	
	public static final String version = "1.5.2R1.0";
	
	@SidedProxy(clientSide="sct.culinarycraft.ClientProxy", serverSide="sct.culinarycraft.CommonProxy")
	public static CommonProxy proxy;
	
	public final static Item pancakes = new ItemPancakes(23000);
	public final static Item bacon = new ItemBacon(23001);
	public final static Item pancakeHelmet = new ItemPancakeHelmet(23002);
	public final static Item handMixer = new ItemHandMixer(23003);
	public final static Item fryingPan = new ItemFryingPan(23004);
	public final static Item rawBacon = new ItemRawBacon(23005);
	public final static Item cookedEgg = new ItemCookedEgg(23006);
	public final static Item salt = new ItemSalt(23007);
	
	public final static Block kitchenTile = new BlockKitchenTile(2300);
	public final static Block blockOven = new BlockOven(2301);
	public final static Block dehydrator = new BlockDehydrator(2302);
	public final static Block countertop = new BlockCountertop(2304);
	
	public final static Block cropBlackPepper = new CropBlackPepper(2320);
	
	public final static Item seedBlackPeppercorn = new ItemBlackPeppercorn(23008);
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		GameRegistry.registerItem(pancakes, "sctpancakes");
		GameRegistry.registerItem(bacon, "sctbacon");
		GameRegistry.registerItem(pancakeHelmet, "sctpancakehelmet");
		GameRegistry.registerItem(handMixer, "sct.handmixer");
		GameRegistry.registerItem(fryingPan, "sct.fryingpan");
		GameRegistry.registerItem(rawBacon, "sct.rawbacon");
		GameRegistry.registerItem(cookedEgg, "sct.cookedegg");
		GameRegistry.registerItem(salt, "sct.salt");
		
		GameRegistry.registerBlock(kitchenTile, "sctkitchentile");
		GameRegistry.registerBlock(blockOven, "sct.oven");
		GameRegistry.registerBlock(dehydrator, "sct.dehydrator");
		GameRegistry.registerBlock(countertop, "sct.countertop");
		
		GameRegistry.registerBlock(cropBlackPepper, "sct.crop.blackpepper");
		
		GameRegistry.registerItem(seedBlackPeppercorn, "sct.blackpeppercorn");
		
		GameRegistry.registerTileEntity(TileEntityOven.class, "entityOven");
		GameRegistry.registerTileEntity(TileEntityDehydrator.class, "entityDehydrator");
		GameRegistry.registerTileEntity(TileEntityCountertop.class, "entityCountertop");
		
		LanguageRegistry.addName(pancakes, "Pancakes");
		LanguageRegistry.addName(bacon, "Bacon");
		LanguageRegistry.addName(pancakeHelmet, "Pancake Helmet");
		LanguageRegistry.addName(handMixer, "Hand Mixer");
		LanguageRegistry.addName(fryingPan, "Frying Pan");
		LanguageRegistry.addName(rawBacon, "Raw Bacon");
		LanguageRegistry.addName(cookedEgg, "Cooked Egg");
		LanguageRegistry.addName(salt, "Salt");
		LanguageRegistry.addName(seedBlackPeppercorn, "Black Peppercorn");
		
		LanguageRegistry.addName(kitchenTile, "Kitchen Tile");
		LanguageRegistry.addName(blockOven, "Oven");
		LanguageRegistry.addName(dehydrator, "Dehydrator");
		LanguageRegistry.addName(cropBlackPepper, "Black Pepper");
		
		RecipeManager.addShapelessOvenRecipe(new ItemStack(bacon), new Object[]{rawBacon,fryingPan});
		RecipeManager.addShapelessOvenRecipe(new ItemStack(cookedEgg), new Object[]{Item.egg,fryingPan});
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		proxy.init();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}