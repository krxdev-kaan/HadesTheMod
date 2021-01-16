package com.krxdevelops.hadesmod.capabilities.exagryph;

import com.krxdevelops.hadesmod.capabilities.coronacht.Coronacht;
import com.krxdevelops.hadesmod.capabilities.coronacht.ICoronacht;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityExagryph
{
    @CapabilityInject(IExagryph.class)
    public static Capability<IExagryph> ADAMANT_RAIL_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IExagryph.class, new Capability.IStorage<IExagryph>() {
                    @Override
                    public NBTBase writeNBT(Capability<IExagryph> capability, IExagryph instance, EnumFacing side)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setLong("lastReloadTicks", instance.getLastReloadTicks());
                        tag.setLong("lastRocketTicks", instance.getLastRocketTicks());
                        tag.setInteger("ammo", instance.getAmmo());
                        return tag;
                    }

                    @Override
                    public void readNBT(Capability<IExagryph> capability, IExagryph instance, EnumFacing side, NBTBase nbt)
                    {
                        NBTTagCompound tag = (NBTTagCompound)nbt;
                        instance.setLastReloadTicks(tag.getLong("lastReloadTicks"));
                        instance.setLastReloadTicks(tag.getLong("lastRocketTicks"));
                        instance.setAmmo(tag.getInteger("ammo"));
                    }
                },
                new Callable<IExagryph>() {
                    @Override
                    public IExagryph call() throws Exception
                    {
                        return new Exagryph();
                    }
                });
    }
}
