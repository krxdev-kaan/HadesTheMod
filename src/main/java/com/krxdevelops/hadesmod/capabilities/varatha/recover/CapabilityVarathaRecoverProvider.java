package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityVarathaRecoverProvider implements ICapabilityProvider
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_varatha_recover");

    private VarathaRecover instance = new VarathaRecover();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY) return CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY.cast(instance);
        else return null;
    }
}
