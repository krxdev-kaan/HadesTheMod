package com.krxdevelops.hadesmod;

import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegisProvider;
import com.krxdevelops.hadesmod.objects.items.ShieldOfChaos;
import com.krxdevelops.hadesmod.proxy.CommonProxy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

        CapabilityAegis.register();
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
