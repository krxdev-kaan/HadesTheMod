package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.entities.*;
import com.krxdevelops.hadesmod.init.BlockInit;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

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

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityCoronachtArrow.class)
                .id(new ResourceLocation("hadesmod", "coronacht_arrow"), id++)
                .name("coronacht_arrow")
                .tracker(160, 1, true)
                .build());

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityExagryphBullet.class)
                .id(new ResourceLocation("hadesmod", "exagryph_bullet"), id++)
                .name("exagryph_bullet")
                .tracker(160, 1, true)
                .build());

        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(EntityExagryphRocket.class)
                .id(new ResourceLocation("hadesmod", "exagryph_rocket"), id++)
                .name("exagryph_rocket")
                .tracker(160, 1, true)
                .build());
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event)
    {
        IModel model;
        try
        {
            model = ModelLoaderRegistry.getModel(new ResourceLocation("hadesmod", "arrow_coronacht"));
        }
        catch(Exception e)
        {
            model = ModelLoaderRegistry.getMissingModel();
        }
        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
        event.getModelRegistry().putObject(new ModelResourceLocation(new ResourceLocation("hadesmod", "arrow_coronacht"), null), bakedModel);
    }
}
