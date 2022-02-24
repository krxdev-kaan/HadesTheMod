package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.AdamantRail;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityExagryphBullet extends EntityArrow
{
    private long ticksWhenSpawned = -1;

    private AdamantRail adamantRail;

    public EntityExagryphBullet(World worldIn)
    {
        super(worldIn);
        this.adamantRail = ItemInit.adamantRail;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.setSize(0.1F, 0.1F);
    }

    public EntityExagryphBullet(World worldIn, double x, double y, double z)
    {
        super(worldIn, x ,y, z);
        this.adamantRail = ItemInit.adamantRail;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.setSize(0.1F, 0.1F);
    }

    public EntityExagryphBullet(World worldIn, EntityLivingBase throwerIn, AdamantRail adamantRail)
    {
        super(worldIn, throwerIn);
        this.adamantRail = adamantRail;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.setSize(0.1F, 0.1F);
    }

    protected float getGravityVelocity()
    {
        return 0.0000F;
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
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    protected void onHit(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(adamantRail.causeDamage(shootingEntity), (float)5F);

            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
        else
        {
            if (!this.world.isRemote)
            {
                ((WorldServer) this.world).spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        this.posX,
                        this.posY,
                        this.posZ,
                        40,
                        0.3D,
                        0.3D,
                        0.3D,
                        0.0D,
                        Block.getIdFromBlock(world.getBlockState(result.getBlockPos()).getBlock())
                );

                this.world.setEntityState(this, (byte)3);
                this.setDead();
            }
        }
    }
}
