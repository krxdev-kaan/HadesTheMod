package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.Globals;
import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphon;
import com.krxdevelops.hadesmod.capabilities.malphon.IMalphon;
import com.krxdevelops.hadesmod.capabilities.player.CapabilityHadesPlayer;
import com.krxdevelops.hadesmod.capabilities.player.IHadesPlayer;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import java.util.Objects;

@Mod.EventBusSubscriber
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

    @Deprecated
    @SubscribeEvent
    public static void onFOVUpdate(FOVUpdateEvent event)
    {
        ItemStack activeStack = event.getEntity().getActiveItemStack();
        if(activeStack.getItem() == ItemInit.heartSeekingBow)
        {
            //event.setNewfov(event.getFov() * 1.5F);
        }
        else if(activeStack.getItem() == ItemInit.adamantRail)
        {
            //event.setNewfov(event.getFov() * 1.5F);
        }
    }

    @SubscribeEvent
    public static void onFallEvent(LivingFallEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayer)
        {
            IHadesPlayer capability = entity.getCapability(CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY, null);
            if(capability.getNextFallCanceled())
            {
                capability.setNextFallCanceled(false);
                if (entity.fallDistance < 12)
                {
                    event.setCanceled(true);
                }
                else
                {
                    entity.fallDistance -= 12;
                }
            }
        }
    }
}
