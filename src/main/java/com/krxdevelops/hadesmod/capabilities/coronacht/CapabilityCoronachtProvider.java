package com.krxdevelops.hadesmod.capabilities.coronacht;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityCoronachtProvider implements ICapabilitySerializable<NBTBase>
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

    @Override
    public NBTBase serializeNBT()
    {
        return CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY.getStorage().writeNBT(CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY.getStorage().readNBT(CapabilityCoronacht.HEART_SEEKING_BOW_CAPABILITY, this.instance, null, nbt);
    }
}
