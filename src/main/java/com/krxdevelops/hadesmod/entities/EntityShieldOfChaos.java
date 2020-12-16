package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.EntityInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import java.util.List;

public class EntityShieldOfChaos extends EntityThrowable
{
    private long ticksWhenSpawned = -1;
    public EntityShieldOfChaos(World worldIn)
    {
        super(worldIn);
    }

    public EntityShieldOfChaos(World worldIn, double x, double y, double z)
    {
        super(worldIn, x ,y, z);
    }

    public EntityShieldOfChaos(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    protected float getGravityVelocity()
    {
        return 0.0001F;
    }

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

        this.world.spawnParticle(
                EnumParticleTypes.LAVA,
                this.posX,
                this.posY,
                this.posZ,
                0.0D,
                0.0D,
                0.0D,
                5
        );
    }

    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)14F);
        }

        if (!this.world.isRemote)
        {
            ((WorldServer)this.world).spawnParticle(
                    EnumParticleTypes.REDSTONE,
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
