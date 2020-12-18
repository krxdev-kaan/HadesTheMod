package com.krxdevelops.hadesmod.render.factory;

import com.krxdevelops.hadesmod.render.renderer.RenderEternalSpear;
import com.krxdevelops.hadesmod.render.renderer.RenderShieldOfChaos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryEternalSpear<T extends Entity> implements IRenderFactory<T>
{
    private final Item item;

    public RenderFactoryEternalSpear(Item item)
    {
        this.item = item;
    }

    @Override
    public Render<? super T> createRenderFor(RenderManager manager)
    {
        return new RenderEternalSpear<T>(manager, this.item, Minecraft.getMinecraft().getRenderItem());
    }
}
