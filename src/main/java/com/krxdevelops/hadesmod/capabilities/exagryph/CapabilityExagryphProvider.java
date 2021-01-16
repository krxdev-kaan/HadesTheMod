package com.krxdevelops.hadesmod.capabilities.exagryph;

import com.krxdevelops.hadesmod.capabilities.coronacht.CapabilityCoronacht;
import com.krxdevelops.hadesmod.capabilities.coronacht.Coronacht;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class CapabilityExagryphProvider  implements ICapabilitySerializable<NBTBase>
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_exagryph");

    private Exagryph instance = new Exagryph();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityExagryph.ADAMANT_RAIL_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityExagryph.ADAMANT_RAIL_CAPABILITY) return CapabilityExagryph.ADAMANT_RAIL_CAPABILITY.cast(instance);
        else return null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return CapabilityExagryph.ADAMANT_RAIL_CAPABILITY.getStorage().writeNBT(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CapabilityExagryph.ADAMANT_RAIL_CAPABILITY.getStorage().readNBT(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, this.instance, null, nbt);
    }
}
