package com.krxdevelops.hadesmod.render.factory;

import com.krxdevelops.hadesmod.render.renderer.RenderShieldOfChaos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryShieldOfChaos<T extends Entity> implements IRenderFactory<T>
{
    private final Item item;

    public RenderFactoryShieldOfChaos(Item item)
    {
        this.item = item;
    }

    @Override
    public Render<? super T> createRenderFor(RenderManager manager)
    {
        return new RenderShieldOfChaos<T>(manager, this.item, Minecraft.getMinecraft().getRenderItem());
    }
}
