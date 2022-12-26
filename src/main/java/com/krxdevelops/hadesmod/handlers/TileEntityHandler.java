package com.krxdevelops.hadesmod.handlers;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.tileEntities.TileEntityInfernalArmPedestal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityInfernalArmPedestal.class, new ResourceLocation("hadesmod:infernal_arm_pedestal"));
    }
}
