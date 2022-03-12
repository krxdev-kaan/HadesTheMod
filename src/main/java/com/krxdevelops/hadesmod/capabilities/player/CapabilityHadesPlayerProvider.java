package com.krxdevelops.hadesmod.capabilities.player;

import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecover;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.VarathaRecover;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityHadesPlayerProvider implements ICapabilitySerializable<NBTBase>
{
    public static ResourceLocation KEY = new ResourceLocation("hadesmod", "capability_hades_player");

    private HadesPlayer instance = new HadesPlayer();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY) return CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY.cast(instance);
        else return null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY.getStorage().writeNBT(CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY.getStorage().readNBT(CapabilityHadesPlayer.HADES_PLAYER_CAPABILITY, this.instance, null, nbt);
    }
}
