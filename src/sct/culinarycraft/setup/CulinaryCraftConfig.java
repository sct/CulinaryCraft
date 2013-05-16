package sct.culinarycraft.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import sct.culinarycraft.CulinaryCraft;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CulinaryCraftConfig {
	
	public static Property idStart;
	
	public static Property decorativeBlockId;
	public static Property ovenBlockId;
	public static Property dehydratorBlockId;
	public static Property countertopBlockId;
	public static Property distributorBlockId;
	public static Property reservoirBlockId;
	
	public static Property blackPepperBlockId;
	public static Property coffeaBlockId;
	
	public static Property rawBaconItemId;
	public static Property baconItemId;
	public static Property cookedEggItemId;
	public static Property saltItemId;
	public static Property pancakesItemId;
	public static Property fryingPanItemId;
	public static Property cuttingBoardItemId;
	public static Property handMixerItemId;
	public static Property standMixerItemId;
	public static Property microwaveItemId;
	public static Property coffeeMachineItemId;
	
	public static Property blackPeppercornItemId;
	public static Property coffeaSeedItemId;
	
	public static File configFolder;
	
	public static void loadCommonConfig(FMLPreInitializationEvent evt)
	{
		Configuration c = new Configuration(evt.getSuggestedConfigurationFile());
		try {
			c.load();
			
			idStart = c.get(Configuration.CATEGORY_BLOCK, "Block.IDStart", 2300);
			idStart.comment = "The block ID to use as the starting point for assignment";
			
			int s = idStart.getInt();
			
			decorativeBlockId = c.getBlock("ID.DecorativeBlock", s++);
			ovenBlockId = c.getBlock("ID.OvenBlock", s++);
			dehydratorBlockId = c.getBlock("ID.DehydratorBlock", s++);
			countertopBlockId = c.getBlock("ID.CountertopBlock", s++);
			distributorBlockId = c.getBlock("ID.DistributorBlock", s++);
			reservoirBlockId = c.getBlock("ID.ReservoirBlock", s++);
			blackPepperBlockId = c.getBlock("ID.BlackPepperCrop", s++);
			coffeaBlockId = c.getBlock("ID.CoffeaCrop", s++);
			
			rawBaconItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.RawBacon", 23100);
			baconItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Bacon", 23101);
			cookedEggItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CookedEgg", 23102);
			saltItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Salt", 23103);
			pancakesItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Pancakes", 23104);
			fryingPanItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.FryingPan", 23105);
			cuttingBoardItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CuttingBoard", 23106);
			handMixerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.HandMixer", 23107);
			standMixerItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.StandMixer", 23108);
			blackPeppercornItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.BlackPeppercorn", 23109);
			coffeaSeedItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CoffeaSeed", 23110);
			microwaveItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.Microwave", 23111);
			coffeeMachineItemId = c.getItem(Configuration.CATEGORY_ITEM, "ID.CoffeeMachine", 23112);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.save();
		}
	}
	
	public static String getConfigBaseFolder()
	{
		return "sct";
	}
	
	public static void setConfigFolderBase(File folder)
    {
        configFolder = new File(folder.getAbsolutePath() + "/" + getConfigBaseFolder() + "/"
                + CulinaryCraft.modId + "/");
    }
	
	public static void extractLang(String[] languages)
    {
        String langResourceBase = "/sct/" + CulinaryCraft.modId + "/lang/";
        for (String lang : languages)
        {
            InputStream is = CulinaryCraft.instance.getClass()
                    .getResourceAsStream(langResourceBase + lang + ".lang");
            try
            {
                File f = new File(configFolder.getAbsolutePath() + "/lang/"
                        + lang + ".lang");
                if (!f.exists())
                    f.getParentFile().mkdirs();
                OutputStream os = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int read = 0;
                while ((read = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, read);
                }
                is.close();
                os.flush();
                os.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
	
	public static void loadLang()
    {
        File f = new File(configFolder.getAbsolutePath() + "/lang/");
        for (File langFile : f.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".lang");
            }
        }))
        {
            try
            {
                Properties langPack = new Properties();
                langPack.load(new FileInputStream(langFile));
                String lang = langFile.getName().replace(".lang", "");
                LanguageRegistry.instance().addStringLocalization(langPack,
                        lang);
            }
            catch (FileNotFoundException x)
            {
                x.printStackTrace();
            }
            catch (IOException x)
            {
                x.printStackTrace();
            }
        }
    }

}
