package com.krxdevelops.hadesmod.capabilities.coronacht;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityCoronachtProvider implements ICapabilityProvider
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_coronacht");

    private Coronacht instance = new Coronacht();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY) return CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY.cast(instance);
        else return null;
    }
}
