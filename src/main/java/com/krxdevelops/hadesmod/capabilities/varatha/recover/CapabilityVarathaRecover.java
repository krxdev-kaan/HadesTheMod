package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityVarathaRecover
{
    @CapabilityInject(IVarathaRecover.class)
    public static Capability<IVarathaRecover> ETERNAL_SPEAR_RECOVER_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IVarathaRecover.class, new Capability.IStorage<IVarathaRecover>() {
                    @Override
                    public NBTBase writeNBT(Capability<IVarathaRecover> capability, IVarathaRecover instance, EnumFacing side)
                    {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setInteger("eternalSpearID", instance.getEternalSpearEntity());
                        return tag;
                    }

                    @Override
                    public void readNBT(Capability<IVarathaRecover> capability, IVarathaRecover instance, EnumFacing side, NBTBase nbt)
                    {
                        instance.setEternalSpearEntity(((NBTTagCompound)nbt).getInteger("eternalSpearID"));
                    }
                },
                new Callable<IVarathaRecover>() {
                    @Override
                    public IVarathaRecover call() throws Exception
                    {
                        return new VarathaRecover();
                    }
                });
    }
}
