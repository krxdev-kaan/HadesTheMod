package com.krxdevelops.hadesmod.capabilities.varatha;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class CapabilityVarathaProvider implements ICapabilityProvider
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_varatha");

    private Varatha instance = new Varatha();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY) return CapabilityVaratha.ETERNAL_SPEAR_CAPABILITY.cast(instance);
        else return null;
    }
}
