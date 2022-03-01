package com.krxdevelops.hadesmod.client;

import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.handlers.GameEventsHandler;
import com.krxdevelops.hadesmod.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

public class GuiOverlay extends Gui {
    public static ResourceLocation chargeBarTexture = new ResourceLocation("hadesmod:textures/gui/charge_bar.png");

    public GuiOverlay()
    {
        this.zLevel = -90F;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = event.getResolution();
        if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE)
        {
            if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.eternalSpear))
            {
                IVaratha capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY, null);
                if (capability.getChargingState())
                {
                    int x = (sc.getScaledWidth() / 2) - (202 / 2);
                    int y = (sc.getScaledHeight() / 2) + 64;

                    mc.getTextureManager().bindTexture(GuiOverlay.chargeBarTexture);
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.scale(0.5f, 0.5f, 1.0f);
                    GlStateManager.translate((sc.getScaledWidth() / 2), (sc.getScaledHeight() / 2), 0.0f);
                    this.drawTexturedModalRect(x, y, 0, 0, 202, 16);
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();

                    double ticksPercentage01 = (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) / 15.0D;
                    int ticksToWidth0196 = (int) ((ticksPercentage01 > 1.0D ? 1.0D : ticksPercentage01) * 196.0D);

                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.scale(0.5f, 0.5f, 1.0f);
                    GlStateManager.translate((sc.getScaledWidth() / 2), (sc.getScaledHeight() / 2), 0.0f);
                    this.drawTexturedModalRect(x, y, 0, 16, 3 + ticksToWidth0196, 16);
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}