package com.krxdevelops.hadesmod.client;

import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphon;
import com.krxdevelops.hadesmod.capabilities.malphon.IMalphon;
import com.krxdevelops.hadesmod.capabilities.stygius.CapabilityStygius;
import com.krxdevelops.hadesmod.capabilities.stygius.IStygius;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiOverlay extends Gui {
    public static ResourceLocation chargeBarTexture = new ResourceLocation("hadesmod:textures/gui/infernal_arm_indicators.png");

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
            if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.stygianBlade))
            {
                IStygius capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityStygius.STYGIAN_BLADE_CAPABILITY, null);
                if (capability.getChargingState())
                {
                    this.renderChargeBar(
                            mc,
                            sc,
                            1.0f,
                            0.4f,
                            0.0f,
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) / 15.0D
                    );
                }
            }
            else if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.eternalSpear))
            {
                IVaratha capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY, null);
                if (capability.getChargingState())
                {
                    this.renderChargeBar(
                            mc,
                            sc,
                            0.0f,
                            0.9f,
                            1.0f,
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) / 15.0D
                    );
                }
            }
            else if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.shieldOfChaos))
            {
                IAegis capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
                if (capability.getChargingState())
                {
                    this.renderChargeBar(
                            mc,
                            sc,
                            1.0f,
                            0.0f,
                            0.0f,
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) / 60.0D
                    );
                }
            }
            else if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.twinFists))
            {
                IMalphon capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
                if (capability.getChargingState())
                {
                    this.renderChargeBar(
                            mc,
                            sc,
                            0.67f,
                            0.0f,
                            0.0f,
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) / 20.0D
                    );
                }
            }
        }
    }

    public void renderChargeBar(Minecraft mc, ScaledResolution sc, float r, float g, float b, double ticksPercentage)
    {
        int x = (sc.getScaledWidth() / 2) - (202 / 2);
        int y = (sc.getScaledHeight() / 2) + 64;

        mc.getTextureManager().bindTexture(GuiOverlay.chargeBarTexture);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.scale(0.25f, 0.25f, 1.0f);
        GlStateManager.translate((sc.getScaledWidth() / 4), (sc.getScaledHeight() / 4), 0.0f);
        this.drawTexturedModalRect(x, y, 0, 0, 202, 16);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

        int ticksToWidth0196 = (int) ((ticksPercentage > 1.0D ? 1.0D : ticksPercentage) * 196.0D);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, 1.0f);
        GlStateManager.scale(0.5f, 0.5f, 1.0f);
        GlStateManager.translate((sc.getScaledWidth() / 2), (sc.getScaledHeight() / 2), 0.0f);
        this.drawTexturedModalRect(x, y, 0, 16, 3 + ticksToWidth0196, 16);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}