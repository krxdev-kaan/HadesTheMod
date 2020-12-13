package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegisProvider;
import com.krxdevelops.hadesmod.items.ShieldOfChaos;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler
{
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> e)
    {
        System.out.println("ATTACHING");
        if (e.getObject().getItem() instanceof ShieldOfChaos)
        {
            System.out.println("ATTACHED");
            e.addCapability(CapabilityAegisProvider.KEY, new CapabilityAegisProvider());
        }
    }
}
