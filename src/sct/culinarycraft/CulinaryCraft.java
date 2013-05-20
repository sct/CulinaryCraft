package sct.culinarycraft;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import sct.culinarycraft.block.BlockCountertop;
import sct.culinarycraft.block.BlockDehydrator;
import sct.culinarycraft.block.BlockHydroponicDistributor;
import sct.culinarycraft.block.BlockHydroponicReservoir;
import sct.culinarycraft.block.BlockKitchenTile;
import sct.culinarycraft.block.BlockOven;
import sct.culinarycraft.block.CropBase;
import sct.culinarycraft.block.ItemBlockCrop;
import sct.culinarycraft.block.liquid.LiquidWine;
import sct.culinarycraft.event.EventHandler;
import sct.culinarycraft.gui.GuiHandler;
import sct.culinarycraft.item.ItemBacon;
import sct.culinarycraft.item.ItemCoffeeMachine;
import sct.culinarycraft.item.ItemCookedEgg;
import sct.culinarycraft.item.ItemCuttingBoard;
import sct.culinarycraft.item.ItemFryingPan;
import sct.culinarycraft.item.ItemHandMixer;
import sct.culinarycraft.item.ItemIngredient;
import sct.culinarycraft.item.ItemMicrowave;
import sct.culinarycraft.item.ItemPancakes;
import sct.culinarycraft.item.ItemRawBacon;
import sct.culinarycraft.item.ItemSalt;
import sct.culinarycraft.item.ItemStandMixer;
import sct.culinarycraft.item.ItemWineBucket;
import sct.culinarycraft.item.seed.ItemBlackPeppercorn;
import sct.culinarycraft.item.seed.ItemCoffeaSeed;
import sct.culinarycraft.net.ClientPacketHandler;
import sct.culinarycraft.recipe.RecipeManager;
import sct.culinarycraft.setup.CulinaryCraftConfig;
import sct.culinarycraft.tile.TileEntityCountertop;
import sct.culinarycraft.tile.TileEntityCrop;
import sct.culinarycraft.tile.TileEntityDehydrator;
import sct.culinarycraft.tile.TileEntityHydroponicDistributor;
import sct.culinarycraft.tile.TileEntityHydroponicReservoir;
import sct.culinarycraft.tile.TileEntityOven;
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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = CulinaryCraft.modId, name = "Culinary Craft", useMetadata = true, version = CulinaryCraft.version)
@NetworkMod(serverSideRequired = false, clientSideRequired = true, 
clientPacketHandlerSpec = @SidedPacketHandler(channels = { CulinaryCraft.modNetworkChannel }, packetHandler = ClientPacketHandler.class))
public class CulinaryCraft {
	
	public static final String modId = "culinarycraft";
	public static final String modNetworkChannel = "CulinaryCraft";
	public static final String version = "1.5.2R1.0";
	
	@Instance(modId)
	public static CulinaryCraft instance;
	
	@SidedProxy(clientSide="sct.culinarycraft.ClientProxy", serverSide="sct.culinarycraft.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	public static EventHandler eventHandler;
	
	public static Block decorative;
	public static Block oven;
	public static Block dehydrator;
	public static Block countertop;
	public static Block reservoir;
	public static Block distributor;
	public static Block crop;
	public static Block cropCoffea;
	public static Block cropBlackPepper;
	public static Block wine;
	
	public static Item pancakes;
	public static Item bacon;
	public static Item pancakeHelmet;;
	public static Item handMixer;
	public static Item fryingPan;
	public static Item rawBacon;
	public static Item cookedEgg;
	public static Item salt;
	public static Item cuttingBoard;
	public static Item standMixer;
	public static Item microwave;
	public static Item coffeeMachine;
	public static Item wineBucket;
	
	public static Item corn;
	public static Item grape;
	
	public static Item seedBlackPeppercorn;
	public static Item seedCoffeaSeed;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		CulinaryCraftConfig.setConfigFolderBase(event.getModConfigurationDirectory());
		
		CulinaryCraftConfig.loadCommonConfig(event);
		
		CulinaryCraftConfig.extractLang(new String[] { "en_US" });
		CulinaryCraftConfig.loadLang();
		
		logger = event.getModLog();
		eventHandler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		
		decorative = new BlockKitchenTile(CulinaryCraftConfig.decorativeBlockId.getInt());
		oven = new BlockOven(CulinaryCraftConfig.ovenBlockId.getInt());
		dehydrator = new BlockDehydrator(CulinaryCraftConfig.dehydratorBlockId.getInt());
		countertop = new BlockCountertop(CulinaryCraftConfig.countertopBlockId.getInt());
		reservoir = new BlockHydroponicReservoir(CulinaryCraftConfig.reservoirBlockId.getInt());
		distributor = new BlockHydroponicDistributor(CulinaryCraftConfig.distributorBlockId.getInt());
		crop = new CropBase(CulinaryCraftConfig.coffeaBlockId.getInt());
		wine = new LiquidWine(3300);
		
		
		pancakes = new ItemPancakes(CulinaryCraftConfig.pancakesItemId.getInt());
		rawBacon = new ItemRawBacon(CulinaryCraftConfig.rawBaconItemId.getInt());
		bacon = new ItemBacon(CulinaryCraftConfig.baconItemId.getInt());
		handMixer = new ItemHandMixer(CulinaryCraftConfig.handMixerItemId.getInt());
		fryingPan = new ItemFryingPan(CulinaryCraftConfig.fryingPanItemId.getInt());
		cookedEgg = new ItemCookedEgg(CulinaryCraftConfig.cookedEggItemId.getInt());
		salt = new ItemSalt(CulinaryCraftConfig.saltItemId.getInt());
		cuttingBoard = new ItemCuttingBoard(CulinaryCraftConfig.cuttingBoardItemId.getInt());
		standMixer = new ItemStandMixer(CulinaryCraftConfig.standMixerItemId.getInt());
		microwave = new ItemMicrowave(CulinaryCraftConfig.microwaveItemId.getInt());
		coffeeMachine = new ItemCoffeeMachine(CulinaryCraftConfig.coffeeMachineItemId.getInt());
		wineBucket = new ItemWineBucket(24000, wine.blockID);
		
		corn = new ItemIngredient(24001).setUnlocalizedName("cornstalk");
		grape = new ItemIngredient(24002).setUnlocalizedName("grape");
		
		seedCoffeaSeed = new ItemCoffeaSeed(CulinaryCraftConfig.coffeaSeedItemId.getInt());
		seedBlackPeppercorn = new ItemBlackPeppercorn(CulinaryCraftConfig.blackPeppercornItemId.getInt());
		
		GameRegistry.registerBlock(decorative, decorative.getUnlocalizedName());
		GameRegistry.registerBlock(oven, oven.getUnlocalizedName());
		GameRegistry.registerBlock(dehydrator, dehydrator.getUnlocalizedName());
		GameRegistry.registerBlock(countertop, countertop.getUnlocalizedName());
		GameRegistry.registerBlock(reservoir, reservoir.getUnlocalizedName());
		GameRegistry.registerBlock(distributor, distributor.getUnlocalizedName());
		GameRegistry.registerBlock(crop, ItemBlockCrop.class,crop.getUnlocalizedName());
		GameRegistry.registerBlock(wine, wine.getUnlocalizedName());
		
		GameRegistry.registerTileEntity(TileEntityCountertop.class, "entityCountertop");
		GameRegistry.registerTileEntity(TileEntityDehydrator.class, "entityDehydrator");
		GameRegistry.registerTileEntity(TileEntityHydroponicDistributor.class, "entityDistributor");
		GameRegistry.registerTileEntity(TileEntityHydroponicReservoir.class, "entityReservoir");
		GameRegistry.registerTileEntity(TileEntityOven.class, "entityOven");
		GameRegistry.registerTileEntity(TileEntityCrop.class, "entityCrop");
		
		RecipeManager.addShapelessOvenRecipe(new ItemStack(bacon), new Object[]{rawBacon,fryingPan});
		RecipeManager.addShapelessOvenRecipe(new ItemStack(cookedEgg), new Object[]{Item.egg,fryingPan});
		
		RecipeManager.addCountertopRecipe(new ItemStack(bacon), cuttingBoard, new Object[]{rawBacon,salt});
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(proxy);
		proxy.init();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		LiquidStack wineStack = new LiquidStack(wine, LiquidContainerRegistry.BUCKET_VOLUME);
		wineStack.setRenderingIcon(wine.getIcon(0, 0));
		
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid("wine", wineStack), new ItemStack(wineBucket), new ItemStack(Item.bucketEmpty)));
		
	}
	
	@ForgeSubscribe
	public void onBucketFill(FillBucketEvent e)
	{
		if(e.current.itemID != Item.bucketEmpty.itemID)
		{
			return;
		}
		ItemStack filledBucket = fillBucket(e.world, e.target);
		if(filledBucket != null)
		{
			e.world.setBlockToAir(e.target.blockX, e.target.blockY, e.target.blockZ);
			e.result = filledBucket;
			e.setResult(Result.ALLOW);
		}
	}
	
	private ItemStack fillBucket(World world, MovingObjectPosition block)
	{
		int blockId = world.getBlockId(block.blockX, block.blockY, block.blockZ);
		if(blockId == wine.blockID) return new ItemStack(wineBucket);
		else return null;
	}
}
