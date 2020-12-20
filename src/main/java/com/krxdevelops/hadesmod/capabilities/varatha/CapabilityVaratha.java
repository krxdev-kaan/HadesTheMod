package com.krxdevelops.hadesmod.capabilities.varatha;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityVaratha
{
    @CapabilityInject(IVaratha.class)
    public static Capability<IVaratha> ETERNAL_SPEAR_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IVaratha.class, new Capability.IStorage<IVaratha>() {
                    @Override
                    public NBTBase writeNBT(Capability<IVaratha> capability, IVaratha instance, EnumFacing side)
                    {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IVaratha> capability, IVaratha instance, EnumFacing side, NBTBase nbt)
                    {
                    }
                },
                new Callable<IVaratha>() {
                    @Override
                    public IVaratha call() throws Exception
                    {
                        return new Varatha();
                    }
                });
    }
}
