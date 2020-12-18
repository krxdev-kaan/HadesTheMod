package com.krxdevelops.hadesmod.proxy;

import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.render.factory.RenderFactoryEternalSpear;
import com.krxdevelops.hadesmod.render.factory.RenderFactoryShieldOfChaos;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Registering Renderers");
        RenderingRegistry.registerEntityRenderingHandler(EntityShieldOfChaos.class, new RenderFactoryShieldOfChaos<EntityShieldOfChaos>(ItemInit.shieldOfChaos));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
