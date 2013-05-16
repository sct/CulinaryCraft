package sct.culinarycraft.event;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sct.culinarycraft.CulinaryCraft;

public class EventHandler {
	
	@ForgeSubscribe
	public void mobDrop(LivingDeathEvent event) {
		if (!event.entityLiving.worldObj.isRemote) {
			if (event.entityLiving instanceof EntityPig) {
				if (((EntityPig) event.entityLiving).getGrowingAge() >= 0) {
					if (event.entityLiving.worldObj.rand.nextFloat() > 0.4F) {
						if (event.source.isFireDamage())
							((EntityPig) event.entityLiving).entityDropItem(new ItemStack(CulinaryCraft.bacon), 0.0F);
						else
							((EntityPig) event.entityLiving).entityDropItem(new ItemStack(CulinaryCraft.rawBacon), 0.0F);
					}
				}
			}
		}
	}

}
