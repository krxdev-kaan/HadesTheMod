package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import com.krxdevelops.hadesmod.capabilities.coronacht.CapabilityCoronacht;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityVarathaRecoverProvider implements ICapabilitySerializable<NBTBase>
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

    @Override
    public NBTBase serializeNBT()
    {
        return CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY.getStorage().writeNBT(CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY.getStorage().readNBT(CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY, this.instance, null, nbt);
    }
}
