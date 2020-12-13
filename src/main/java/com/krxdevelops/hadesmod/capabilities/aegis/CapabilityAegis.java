package com.krxdevelops.hadesmod.capabilities.aegis;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityAegis
{
    @CapabilityInject(IAegis.class)
    public static Capability<IAegis> SHIELD_OF_CHAOS_CAPABILITIES = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IAegis.class, new Capability.IStorage<IAegis>() {
                    @Override
                    public NBTBase writeNBT(Capability<IAegis> capability, IAegis instance, EnumFacing side)
                    {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IAegis> capability, IAegis instance, EnumFacing side, NBTBase nbt)
                    {
                    }
                },
                new Callable<IAegis>() {
                    @Override
                    public IAegis call() throws Exception
                    {
                        return new Aegis();
                    }
                });
    }
}

