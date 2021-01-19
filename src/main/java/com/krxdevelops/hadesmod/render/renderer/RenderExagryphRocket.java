package com.krxdevelops.hadesmod.render.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderExagryphRocket<T extends Entity> extends Render<T>
{
    public RenderExagryphRocket(RenderManager renderManager)
    {
        super(renderManager);
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableRescaleNormal();

        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks + 90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
        IBakedModel ibakedmodel;
        try
        {
            ibakedmodel = modelmanager.getModel(new ModelResourceLocation(new ResourceLocation("hadesmod", "rocket_bomb_exagryph"), null));
        }
        catch (Exception e)
        {
            ibakedmodel = null;
        }
        blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
