package com.krxdevelops.hadesmod;

import com.krxdevelops.hadesmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = "hadesmod", name = "Hades: The Mod", version = "1.0.0", useMetadata = true)
public class HadesMod
{
    @Mod.Instance("hadesmod")
    public static HadesMod instance;

    @SidedProxy(clientSide = "com.krxdevelops.hadesmod.proxy.ClientProxy", serverSide = "com.krxdevelops.hadesmod.proxy.CommonProxy")
    public  static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Napiyim Anani Gotten mi sikiyim ?");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
