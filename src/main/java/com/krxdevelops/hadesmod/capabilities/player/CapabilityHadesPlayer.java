package com.krxdevelops.hadesmod.capabilities.player;

import com.krxdevelops.hadesmod.capabilities.malphon.IMalphon;
import com.krxdevelops.hadesmod.capabilities.malphon.Malphon;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityHadesPlayer
{
    @CapabilityInject(IHadesPlayer.class)
    public static Capability<IHadesPlayer> HADES_PLAYER_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHadesPlayer.class, new Capability.IStorage<IHadesPlayer>() {
                    @Override
                    public NBTBase writeNBT(Capability<IHadesPlayer> capability, IHadesPlayer instance, EnumFacing side)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setBoolean("isNextFallCanceled", instance.getNextFallCanceled());
                        return tag;
                    }

                    @Override
                    public void readNBT(Capability<IHadesPlayer> capability, IHadesPlayer instance, EnumFacing side, NBTBase nbt)
                    {
                        instance.setNextFallCanceled(((NBTTagCompound)nbt).getBoolean("isNextFallCanceled"));
                    }
                },
                new Callable<IHadesPlayer>() {
                    @Override
                    public IHadesPlayer call() throws Exception
                    {
                        return new HadesPlayer();
                    }
                });
    }
}
