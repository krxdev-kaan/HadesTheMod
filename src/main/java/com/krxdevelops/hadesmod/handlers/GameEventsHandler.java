package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.client.GuiOverlay;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.EternalSpearRecoverItem;
import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import java.util.Objects;

public class GameEventsHandler
{
    private GuiOverlay guiOverlay = new GuiOverlay();
    private float zLevel;
    /* @SubscribeEvent
    public static void itemDropEvent(ItemTossEvent event)
    {
        if (event.isCancelable())
        {
            if (true)
            {
                ItemStack itemStack = event.getEntityItem().getItem();
                if (itemStack.getItem() instanceof EternalSpearRecoverItem)
                {
                    event.setResult(Event.Result.DENY);
                    event.setCanceled(true);
                }
            }
        }
    } */

    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event)
    {
        ItemStack activeStack = event.getEntity().getActiveItemStack();
        if (activeStack.getItem() == ItemInit.heartSeekingBow)
        {
            //event.setNewfov(event.getFov() * 1.0F);
        }
        else if (activeStack.getItem() == ItemInit.adamantRail)
        {
            //event.setNewfov(event.getFov() * 1.0F);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event)
    {
    }

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
                event.getModelPlayer().bipedRightArm.rotateAngleX = (float) Math.toRadians(-200f + ((1.0f - ticksPassedPercentage01) * 200.0f));
                event.getModelPlayer().bipedLeftArm.rotateAngleX = (float) Math.toRadians(-45f + ((1.0f - ticksPassedPercentage01) * 45.0f));
            }
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
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event)
    {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event)
    {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event)
    {
    }
}
