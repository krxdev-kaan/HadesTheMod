package com.krxdevelops.hadesmod.proxy;

import com.krxdevelops.hadesmod.entities.EntityCoronachtArrow;
import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.render.renderer.RenderCoronachtArrow;
import com.krxdevelops.hadesmod.render.renderer.RenderEternalSpear;
import com.krxdevelops.hadesmod.render.renderer.RenderShieldOfChaos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityShieldOfChaos.class, m -> new RenderShieldOfChaos<>(m, ItemInit.shieldOfChaos, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityEternalSpear.class, m -> new RenderEternalSpear<>(m, ItemInit.eternalSpear, Minecraft.getMinecraft().getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCoronachtArrow.class, m -> new RenderCoronachtArrow<>(m));
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
}
