package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphon;
import com.krxdevelops.hadesmod.capabilities.malphon.IMalphon;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void setupPlayerRotations(ModelPlayerEvent.SetupAngles.Post event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if(player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.eternalSpear))
        {
            IVaratha capability = player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY, null);
            if (capability.getChargingState())
            {
                float ticksPassedPercentage01 = ((float)(player.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks()) / 15.0f;
                if (ticksPassedPercentage01 > 1.0D) ticksPassedPercentage01 = 1.0f;
                event.getModelPlayer().bipedRightArm.rotateAngleX = (float)Math.toRadians(-200f + ((1.0f - ticksPassedPercentage01) * 200.0f));
                event.getModelPlayer().bipedLeftArm.rotateAngleX = (float)Math.toRadians(-45f + ((1.0f - ticksPassedPercentage01) * 45.0f));
            }
        }
        else if(player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.twinFists))
        {
            IMalphon capability = player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
            if (capability.getChargingState())
            {
                float ticksPassedPercentage01 = ((float)(player.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks()) / 20.0f;
                if (ticksPassedPercentage01 > 1.0D) ticksPassedPercentage01 = 1.0f;
                event.getModelPlayer().bipedRightArm.rotateAngleX = (float)Math.toRadians(-ticksPassedPercentage01 * 60.0f);
                event.getModelPlayer().bipedLeftArm.rotateAngleX = (float)Math.toRadians(-45f + ((1.0f - ticksPassedPercentage01) * 45.0f));
                event.getModelPlayer().bipedRightArm.rotateAngleY = (float)Math.toRadians(ticksPassedPercentage01 * 180.0f);
            }
        }
        else if(player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(ItemInit.adamantRail))
        {
            event.getModelPlayer().bipedRightArm.rotateAngleX = (float)Math.toRadians(-90f);
            event.getModelPlayer().bipedRightArm.rotateAngleY = (float)Math.toRadians(-20f);
            event.getModelPlayer().bipedLeftArm.rotateAngleX = (float)Math.toRadians(-90f);
            event.getModelPlayer().bipedLeftArm.rotateAngleY = (float)Math.toRadians(50f);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderHeldItem(RenderItemEvent.Held.Pre event)
    {
        if(event.getItem().getItem().equals(ItemInit.eternalSpear))
        {
            IVaratha capability = event.getItem().getCapability(CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY, null);
            if (capability.getChargingState())
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0f, 0.0f, 0.2f);
                GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            }
        }
        else if(event.getItem().getItem().equals(ItemInit.twinFists))
        {
            IMalphon capability = event.getItem().getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
            if (capability.getChargingState())
            {
                AbstractClientPlayer player = (AbstractClientPlayer)event.getEntity();
                boolean isSlim = player.getSkinType() == "slim";

                GlStateManager.pushMatrix();
                GlStateManager.translate(isSlim ? -0.06f : 0.0f, -0.25f, isSlim ? -0.07 : 0.00f);
                if (isSlim) GlStateManager.scale(1.1f, 1.1f, 1.1f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        else if(event.getItem().getItem().equals(ItemInit.adamantRail))
        {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(-20.0f, 0.0f, 1.0f, 0.0f);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderHeldItem(RenderItemEvent.Held.Post event)
    {
        if(event.getItem().getItem().equals(ItemInit.eternalSpear))
        {
            IVaratha capability = event.getItem().getCapability(CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY, null);
            if (capability.getChargingState())
            {
                GlStateManager.popMatrix();
            }
        }
        else if(event.getItem().getItem().equals(ItemInit.twinFists))
        {
            IMalphon capability = event.getItem().getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
            if (capability.getChargingState())
            {
                GlStateManager.popMatrix();
            }
        }
        else if(event.getItem().getItem().equals(ItemInit.adamantRail))
        {
            GlStateManager.popMatrix();
        }
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event)
    {
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event)
    {
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event)
    {
    }
}
