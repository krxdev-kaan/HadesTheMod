package com.krxdevelops.hadesmod.render.renderer;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.ShieldOfChaos;
import com.krxdevelops.hadesmod.tileEntities.TileEntityInfernalArmPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderInfernalArmPedestal extends TileEntitySpecialRenderer<TileEntityInfernalArmPedestal>
{
    @Override
    public void render(TileEntityInfernalArmPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        ItemStack stackOnEntity = te.itemStacks.get(0);
        if (!stackOnEntity.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(x, y + 0.75F, z);

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.5F, 0.0F, 0.5F);

            for (int i = 0; i < 3; i++)
            {
                GlStateManager.rotate((te.getWorld().getTotalWorldTime() + i * 120 + partialTicks) % 360, 0.0F, 1.0F, 0.0F);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5F, 0.3F, 0.5F);
                GlStateManager.disableLighting();
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160, 8);
                Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(ItemInit.titanblood, 1), ItemCameraTransforms.TransformType.GROUND);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                GlStateManager.rotate(-((te.getWorld().getTotalWorldTime() + i * 120 + partialTicks) % 360), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) % 360, 0.0F, 1.0F, 0.0F);

            if (stackOnEntity.getItem() instanceof ShieldOfChaos)
            {
                GlStateManager.translate(0.0F, 0.5F, 0.0F);
                GlStateManager.rotate(70F, 1.0F, 0.0F, 0.0F);
            }

            GlStateManager.disableLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 160, 8);
            Minecraft.getMinecraft().getRenderItem().renderItem(stackOnEntity, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }
}
