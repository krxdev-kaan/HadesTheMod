package com.krxdevelops.hadesmod.capabilities.malphon;

import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.Varatha;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityMalphonProvider implements ICapabilityProvider
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_malphon");

    private Malphon instance = new Malphon();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityMalphon.TWIN_FISTS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityMalphon.TWIN_FISTS_CAPABILITY) return CapabilityMalphon.TWIN_FISTS_CAPABILITY.cast(instance);
        else return null;
    }
}
