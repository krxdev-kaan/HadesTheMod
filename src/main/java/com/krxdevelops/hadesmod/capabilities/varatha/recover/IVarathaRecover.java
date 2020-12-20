package com.krxdevelops.hadesmod.capabilities.varatha.recover;

import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface IVarathaRecover
{
    public EntityEternalSpear getEternalSpearEntity();
    public void setEternalSpearEntity(EntityEternalSpear entityEternalSpear);
}
