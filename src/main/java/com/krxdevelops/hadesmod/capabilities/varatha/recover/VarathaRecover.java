package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class VarathaRecover implements IVarathaRecover
{
    protected int entityEternalSpear;

    public VarathaRecover()
    {
        entityEternalSpear = -1;
    }

    @Override
    public int getEternalSpearEntity()
    {
        return entityEternalSpear;
    }

    @Override
    public void setEternalSpearEntity(int entityLivingBase)
    {
        this.entityEternalSpear = entityLivingBase;
    }
}
