package com.krxdevelops.hadesmod.capabilities.stygius;

import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.Varatha;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityStygius
{
    @CapabilityInject(IStygius.class)
    public static Capability<IStygius> STYGIAN_BLADE_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IStygius.class, new Capability.IStorage<IStygius>() {
                    @Override
                    public NBTBase writeNBT(Capability<IStygius> capability, IStygius instance, EnumFacing side)
                    {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IStygius> capability, IStygius instance, EnumFacing side, NBTBase nbt)
                    {
                    }
                },
                new Callable<IStygius>() {
                    @Override
                    public IStygius call() throws Exception
                    {
                        return new Stygius();
                    }
                });
    }
}
