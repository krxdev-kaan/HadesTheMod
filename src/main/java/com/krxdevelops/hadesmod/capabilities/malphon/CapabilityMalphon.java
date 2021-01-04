package com.krxdevelops.hadesmod.capabilities.malphon;

import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.Varatha;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityMalphon
{
    @CapabilityInject(IMalphon.class)
    public static Capability<IMalphon> TWIN_FISTS_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IMalphon.class, new Capability.IStorage<IMalphon>() {
                    @Override
                    public NBTBase writeNBT(Capability<IMalphon> capability, IMalphon instance, EnumFacing side)
                    {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IMalphon> capability, IMalphon instance, EnumFacing side, NBTBase nbt)
                    {
                    }
                },
                new Callable<IMalphon>() {
                    @Override
                    public IMalphon call() throws Exception
                    {
                        return new Malphon();
                    }
                });
    }
}
