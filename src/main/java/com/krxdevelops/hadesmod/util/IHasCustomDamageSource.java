package com.krxdevelops.hadesmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public interface IHasCustomDamageSource
{
    public DamageSource causeDamage(Entity entity);
}
