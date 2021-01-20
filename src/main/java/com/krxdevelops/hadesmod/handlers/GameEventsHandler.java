package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.EternalSpearRecoverItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class GameEventsHandler
{
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

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event)
    {
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event)
    {
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event)
    {
    }
}
