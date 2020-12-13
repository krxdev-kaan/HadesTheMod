package com.krxdevelops.hadesmod.proxy;

import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.ShieldOfChaos;
import com.krxdevelops.hadesmod.render.RenderShieldOfChaos;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.Sys;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityShieldOfChaos.class, new RenderShieldOfChaos<EntityShieldOfChaos>(ItemInit.shieldOfChaos));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
