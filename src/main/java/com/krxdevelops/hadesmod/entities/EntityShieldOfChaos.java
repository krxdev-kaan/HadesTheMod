package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.EntityInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.lwjgl.Sys;

public class EntityShieldOfChaos extends EntityThrowable
{
    public EntityShieldOfChaos(World worldIn)
    {
        super(worldIn);
    }

    public EntityShieldOfChaos(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public void onUpdate()
    {
        super.onUpdate();
        if (!world.isRemote)
        {
            this.world.spawnParticle(
                    EnumParticleTypes.DRIP_LAVA,
                    this.posX,
                    this.posY,
                    this.posZ,
                    0.0D,
                    0.0D,
                    0.0D
            );
        }
    }

    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)14F);
        }

        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
