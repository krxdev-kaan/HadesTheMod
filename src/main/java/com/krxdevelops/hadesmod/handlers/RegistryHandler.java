package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.BlockInit;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        System.out.println("Registering Items");
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        System.out.println("Registering Blocks");
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        System.out.println("Registering Item Models");
        for (Item item : ItemInit.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel) item).registerModels();
            }
        }

        System.out.println("Registering Block Models");
        for (Block block : BlockInit.BLOCKS)
        {
            if (block instanceof IHasModel)
            {
                ((IHasModel) block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event)
    {
        System.out.println("Registering Entity");

        int id = 0;

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityShieldOfChaos.class)
                .id(new ResourceLocation("hadesmod", "shield_of_chaos"), id++)
                .name("shield_of_chaos")
                .tracker(160, 3, true)
                .build());

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityEternalSpear.class)
                .id(new ResourceLocation("hadesmod", "eternal_spear"), id++)
                .name("eternal_spear")
                .tracker(160, 1, true)
                .build());
    }
}
