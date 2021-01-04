package com.krxdevelops.hadesmod.capabilities.coronacht;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityCoronacht
{
    @CapabilityInject(ICoronacht.class)
    public static Capability<ICoronacht> HEART_SEEKING_BOW_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ICoronacht.class, new Capability.IStorage<ICoronacht>() {
                    @Override
                    public NBTBase writeNBT(Capability<ICoronacht> capability, ICoronacht instance, EnumFacing side)
                    {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<ICoronacht> capability, ICoronacht instance, EnumFacing side, NBTBase nbt)
                    {
                    }
                },
                new Callable<ICoronacht>() {
                    @Override
                    public ICoronacht call() throws Exception
                    {
                        return new Coronacht();
                    }
                });
    }
}
