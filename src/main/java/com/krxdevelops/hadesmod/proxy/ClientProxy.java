package com.krxdevelops.hadesmod.proxy;

import com.krxdevelops.hadesmod.client.GuiOverlay;
import com.krxdevelops.hadesmod.entities.EntityCoronachtArrow;
import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.entities.EntityExagryphRocket;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.handlers.GameEventsHandler;
import com.krxdevelops.hadesmod.handlers.RenderHandler;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.DebugItem;
import com.krxdevelops.hadesmod.render.renderer.RenderCoronachtArrow;
import com.krxdevelops.hadesmod.render.renderer.RenderEternalSpear;
import com.krxdevelops.hadesmod.render.renderer.RenderExagryphRocket;
import com.krxdevelops.hadesmod.render.renderer.RenderShieldOfChaos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("Registering Renderers");

        MinecraftForge.EVENT_BUS.register(new RenderHandler());     // Custom Use Animations
        MinecraftForge.EVENT_BUS.register(new GuiOverlay());        // Custom Item Overlays etc.
        MinecraftForge.EVENT_BUS.register(ItemInit.debugItem);      // Custom keyboard/mouse input

        RenderingRegistry.registerEntityRenderingHandler(EntityShieldOfChaos.class, m -> new RenderShieldOfChaos<>(m, ItemInit.shieldOfChaos, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEternalSpear.class, m -> new RenderEternalSpear<>(m, ItemInit.eternalSpear, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCoronachtArrow.class, m -> new RenderCoronachtArrow<>(m));
        RenderingRegistry.registerEntityRenderingHandler(EntityExagryphRocket.class, m -> new RenderExagryphRocket<>(m));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
