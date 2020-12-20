package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class VarathaRecover implements IVarathaRecover
{
    protected EntityEternalSpear entityEternalSpear;

    public VarathaRecover()
    {
        entityEternalSpear = null;
    }

    @Override
    public EntityEternalSpear getEternalSpearEntity()
    {
        return entityEternalSpear;
    }

    @Override
    public void setEternalSpearEntity(EntityEternalSpear entityLivingBase)
    {
        this.entityEternalSpear = entityLivingBase;
    }
}
