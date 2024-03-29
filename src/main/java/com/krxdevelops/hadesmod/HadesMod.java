package com.krxdevelops.hadesmod;

import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.coronacht.CapabilityCoronacht;
import com.krxdevelops.hadesmod.capabilities.exagryph.CapabilityExagryph;
import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphon;
import com.krxdevelops.hadesmod.capabilities.player.CapabilityHadesPlayer;
import com.krxdevelops.hadesmod.capabilities.stygius.CapabilityStygius;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecover;
import com.krxdevelops.hadesmod.network.DebugItemModChangeMessage;
import com.krxdevelops.hadesmod.network.HadesModPacketHandler;
import com.krxdevelops.hadesmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;


@Mod(modid = "hadesmod", name = "Hades: The Mod", version = "1.0.0", useMetadata = true)
public class HadesMod
{
    @Mod.Instance("hadesmod")
    public static HadesMod instance;

    @SidedProxy(clientSide = "com.krxdevelops.hadesmod.proxy.ClientProxy", serverSide = "com.krxdevelops.hadesmod.proxy.CommonProxy")
    public static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);

        // Register Capabilities
        CapabilityAegis.register();
        CapabilityVarathaRecover.register();
        CapabilityVaratha.register();
        CapabilityCoronacht.register();
        CapabilityMalphon.register();
        CapabilityExagryph.register();
        CapabilityStygius.register();
        CapabilityHadesPlayer.register();

        // Register Packets
        HadesModPacketHandler.INSTANCE.registerMessage(DebugItemModChangeMessage.DebugItemModChangeMessageHandler.class, DebugItemModChangeMessage.class, 0, Side.SERVER);
        HadesModPacketHandler.INSTANCE.registerMessage(DebugItemModChangeMessage.DebugItemModChangeMessageHandler.class, DebugItemModChangeMessage.class, 1, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}
