package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegisProvider;
import com.krxdevelops.hadesmod.capabilities.coronacht.CapabilityCoronachtProvider;
import com.krxdevelops.hadesmod.capabilities.exagryph.CapabilityExagryphProvider;
import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphonProvider;
import com.krxdevelops.hadesmod.capabilities.stygius.CapabilityStygiusProvider;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVarathaProvider;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecoverProvider;
import com.krxdevelops.hadesmod.items.*;
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
        if (e.getObject().getItem() instanceof ShieldOfChaos)
        {
            e.addCapability(CapabilityAegisProvider.KEY, new CapabilityAegisProvider());
        }
        else if (e.getObject().getItem() instanceof EternalSpearRecoverItem)
        {
            e.addCapability(CapabilityVarathaRecoverProvider.KEY, new CapabilityVarathaRecoverProvider());
        }
        else if (e.getObject().getItem() instanceof EternalSpear)
        {
            e.addCapability(CapabilityVarathaProvider.KEY, new CapabilityVarathaProvider());
        }
        else if (e.getObject().getItem() instanceof HeartSeekingBow)
        {
            e.addCapability(CapabilityCoronachtProvider.KEY, new CapabilityCoronachtProvider());
        }
        else if (e.getObject().getItem() instanceof TwinFists)
        {
            e.addCapability(CapabilityMalphonProvider.KEY, new CapabilityMalphonProvider());
        }
        else if (e.getObject().getItem() instanceof AdamantRail)
        {
            e.addCapability(CapabilityExagryphProvider.KEY, new CapabilityExagryphProvider());
        }
        else if (e.getObject().getItem() instanceof StygiusSword)
        {
            e.addCapability(CapabilityStygiusProvider.KEY, new CapabilityStygiusProvider());
        }
    }
}
