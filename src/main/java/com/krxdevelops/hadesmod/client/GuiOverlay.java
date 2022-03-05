package com.krxdevelops.hadesmod.client;

import com.krxdevelops.hadesmod.Globals;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import com.krxdevelops.hadesmod.capabilities.exagryph.CapabilityExagryph;
import com.krxdevelops.hadesmod.capabilities.exagryph.IExagryph;
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
    public static ResourceLocation infernalArmIndicators = new ResourceLocation("hadesmod:textures/gui/infernal_arm_indicators.png");

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
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks(),
                            15.0D
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
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks(),
                            15.0D
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
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks(),
                            60.0D
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
                            (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks(),
                            20.0D
                    );
                }
            }
            else if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.adamantRail))
            {
                IExagryph capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, null);
                this.renderAmmoBar(
                    mc,
                    sc,
                    capability.getAmmo(),
                    capability.getMaxAmmo(),
                    capability.getReloadingState(),
                    (double)(mc.world.getTotalWorldTime() - capability.getLastReloadTicks()) + event.getPartialTicks()
                );
            }
            else if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.debugItem))
            {
                String mode = Globals.isModeAdd ? "Add " : "Substract ";
                String on = Globals.selectorXYZ == 1 ? "X" : Globals.selectorXYZ == 2 ? "Y" : "Z";
                mc.fontRenderer.drawString(mode + on, 0, 0, 0xFFFFFFFF, true);
                mc.fontRenderer.drawString("X: " + Globals.twinFistSlimX, 0, 24, 0xFFFFFFFF, true);
                mc.fontRenderer.drawString("Y: " + Globals.twinFistSlimY, 0, 48, 0xFFFFFFFF, true);
                mc.fontRenderer.drawString("Z: " + Globals.twinFistSlimZ, 0, 72, 0xFFFFFFFF, true);
            }
        }
    }

    public void renderChargeBar(Minecraft mc, ScaledResolution sc, float r, float g, float b, double ticksPassed, double tickMultiplier)
    {
        int x = (sc.getScaledWidth() / 2) - (202 / 2);
        int y = (sc.getScaledHeight() / 2) + 64;

        mc.getTextureManager().bindTexture(GuiOverlay.infernalArmIndicators);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.scale(0.5f, 0.5f, 1.0f);
        GlStateManager.translate((sc.getScaledWidth() / 2), (sc.getScaledHeight() / 2), 0.0f);
        this.drawTexturedModalRect(x, y, 0, 0, 202, 16);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

        double ticksPercentage = ticksPassed / tickMultiplier;
        int ticksToWidth0196 = (int)((ticksPercentage > 1.0D ? 1.0D : ticksPercentage) * 196.0D);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, 1.0f);
        GlStateManager.scale(0.5f, 0.5f, 1.0f);
        GlStateManager.translate((sc.getScaledWidth() / 2), (sc.getScaledHeight() / 2), 0.0f);
        this.drawTexturedModalRect(x, y, 0, 16, 3 + ticksToWidth0196, 16);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void renderAmmoBar(Minecraft mc, ScaledResolution sc, int ammoCount, int maxAmmo, boolean isReloading, double ticksPassed)
    {
        int x = sc.getScaledWidth() - 21;
        int y = (sc.getScaledHeight() / 2) - (202 / 2);

        mc.getTextureManager().bindTexture(GuiOverlay.infernalArmIndicators);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(x, y, 32, 32, 16, 202);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

        if (ammoCount > 0)
        {
            int heightForCurrentAmmo = ammoCount >= maxAmmo ? 202 : 21 + (16 * (ammoCount - 1));

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y + (202 - heightForCurrentAmmo), 16, 32 + (202 - heightForCurrentAmmo), 16, heightForCurrentAmmo);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
        else if (isReloading)
        {
            double ticksPercentage = ticksPassed / 20.0D;
            int ticksToHeight0202 = 3 + (int)((ticksPercentage > 1.0D ? 1.0D : ticksPercentage) * 196.0D);

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y + (202 - ticksToHeight0202), 16, 32 + (202 - ticksToHeight0202), 16, ticksToHeight0202);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}