package com.krxdevelops.hadesmod.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityEternalSpear extends EntityThrowable
{
    private long ticksWhenSpawned = -1;

    public EntityEternalSpear(World worldIn)
    {
        super(worldIn);
    }

    public EntityEternalSpear(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityEternalSpear(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    protected float getGravityVelocity()
    {
        return 0.0001F;
    }

    @Override
    public void onUpdate()
    {
        if (!world.isRemote)
        {
            if (ticksWhenSpawned == -1) ticksWhenSpawned = world.getTotalWorldTime();
            else {
                double ticksPassed = world.getTotalWorldTime() - ticksWhenSpawned;
                if (ticksPassed > 60) this.setDead();
            }
        }

        super.onUpdate();
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)18F);
        }

        if (!this.world.isRemote)
        {
            ((WorldServer)this.world).spawnParticle(
                    EnumParticleTypes.BLOCK_CRACK,
                    this.posX,
                    this.posY,
                    this.posZ,
                    40,
                    0.3D,
                    0.3D,
                    0.3D,
                    0.0D
            );

            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
