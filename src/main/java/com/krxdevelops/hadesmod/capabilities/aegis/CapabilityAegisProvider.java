package com.krxdevelops.hadesmod.capabilities.aegis;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityAegisProvider implements ICapabilityProvider
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_aegis");

    private Aegis instance = new Aegis();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES) return CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES.cast(instance);
        else return null;
    }
}
