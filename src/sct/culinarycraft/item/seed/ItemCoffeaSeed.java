package sct.culinarycraft.item.seed;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import sct.culinarycraft.CulinaryCraft;
import sct.culinarycraft.gui.CulinaryCraftCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoffeaSeed extends Item implements IPlantable {
	
	public ItemCoffeaSeed(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CulinaryCraftCreativeTab.tab);
		setUnlocalizedName("culinary.seed.coffea");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("coffeaSeed");
	}
	
	public Icon getItemIcon() {
		return itemIcon;
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            int i1 = world.getBlockId(par4, par5, par6);
            Block soil = Block.blocksList[i1];

            if (soil != null && soil.canSustainPlant(world, par4, par5, par6, ForgeDirection.UP, this) && world.isAirBlock(par4, par5 + 1, par6))
            {
                world.setBlock(par4, par5 + 1, par6, CulinaryCraft.crop.blockID);
                --par1ItemStack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPlantMetadata(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
