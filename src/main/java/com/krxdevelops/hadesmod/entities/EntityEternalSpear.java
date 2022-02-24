package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.EternalSpear;
import javafx.scene.chart.Axis;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityEternalSpear extends EntityArrow
{
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private int inData;
    private int ticksInAir;
    private int ticksInGround;
    private int knockbackStrength;
    private float damage;

    protected EntityLivingBase piercedEntity;
    protected double pierceOffsetX = -1;
    protected double pierceOffsetY = -1;
    protected double pierceOffsetZ = -1;

    private EternalSpear eternalSpear;

    public EntityEternalSpear(World worldIn)
    {
        super(worldIn);
        this.eternalSpear = ItemInit.eternalSpear;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.damage = 18.0F;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public EntityEternalSpear(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.eternalSpear = ItemInit.eternalSpear;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.damage = 18.0F;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public EntityEternalSpear(World worldIn, EntityLivingBase throwerIn, EternalSpear eternalSpear)
    {
        super(worldIn, throwerIn);
        this.eternalSpear = eternalSpear;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.damage = 18.0F;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    protected float getGravityVelocity()
    {
        return 0.0001F;
    }

    @Override
    public void onUpdate()
    {
        if (piercedEntity == null || piercedEntity.isDead)
        {
            if (!this.world.isRemote)
            {
                this.setFlag(6, this.isGlowing());
            }

            if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
            {
                float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
                this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * (180D / Math.PI));
                this.prevRotationYaw = this.rotationYaw;
                this.prevRotationPitch = this.rotationPitch;
            }

            BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR)
            {
                AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

                if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
                {
                    this.inGround = true;
                }
            }

            if (this.arrowShake > 0)
            {
                --this.arrowShake;
            }

            if (this.inGround)
            {
                int j = block.getMetaFromState(iblockstate);

                if ((block != this.inTile || j != this.inData) && !this.world.collidesWithAnyBlock(this.getEntityBoundingBox().grow(0.05D)))
                {
                    this.inGround = false;
                    this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
                    this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
                    this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
                    this.ticksInGround = 0;
                    this.ticksInAir = 0;
                }
                else
                {
                    ++this.ticksInGround;

                    if (this.ticksInGround >= 1200)
                    {
                        this.setDead();
                    }
                }

                ++this.timeInGround;
            }
            else
            {
                this.timeInGround = 0;
                ++this.ticksInAir;
                Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
                vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
                vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

                if (raytraceresult != null)
                {
                    vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
                }

                Entity entity = this.findEntityOnPath(vec3d1, vec3d);

                if (entity != null)
                {
                    raytraceresult = new RayTraceResult(entity);
                }

                if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

                    if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer))
                    {
                        raytraceresult = null;
                    }
                }

                if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
                {
                    this.onHit(raytraceresult);
                }

                if (this.getIsCritical())
                {
                    for (int k = 0; k < 4; ++k)
                    {
                        this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) k / 4.0D, this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                    }
                }

                this.posX += this.motionX;
                this.posY += this.motionY;
                this.posZ += this.motionZ;
                float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

                for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
                {
                    ;
                }

                while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
                {
                    this.prevRotationPitch += 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw < -180.0F)
                {
                    this.prevRotationYaw -= 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
                {
                    this.prevRotationYaw += 360.0F;
                }

                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
                float f1 = 0.99F;
                float f2 = 0.05F;

                if (this.isInWater())
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        float f3 = 0.25F;
                        this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                    }

                    f1 = 0.6F;
                }

                if (this.isWet())
                {
                    this.extinguish();
                }

                this.motionX *= (double) f1;
                this.motionY *= (double) f1;
                this.motionZ *= (double) f1;

                if (!this.hasNoGravity())
                {
                    this.motionY -= 0.05000000074505806D;
                }

                this.setPosition(this.posX, this.posY, this.posZ);
                this.doBlockCollisions();
            }
        }
        else
            {
                if(!this.world.isRemote)
                {
                    if (!piercedEntity.isDead)
                    {
                        this.setPosition(piercedEntity.posX + pierceOffsetX,
                                piercedEntity.posY + pierceOffsetY,
                                piercedEntity.posZ + pierceOffsetZ);
                    }
                }
            }
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ItemInit.eternalSpear, 1);
    }

    @Override
    protected void onHit(RayTraceResult result)
    {
        Entity entity = result.entityHit;

        if (entity != null)
        {
            DamageSource damagesource;

            if (this.shootingEntity == null)
            {
                damagesource = eternalSpear.causeDamage(this);
            }
            else
            {
                damagesource = eternalSpear.causeDamage(shootingEntity);
            }

            if (entity.attackEntityFrom(damagesource, this.damage))
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                    if (!this.world.isRemote)
                    {
                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                    }

                    if (this.knockbackStrength > 0)
                    {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F)
                        {
                            entitylivingbase.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
                        }
                    }

                    if (this.shootingEntity instanceof EntityLivingBase)
                    {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                    }

                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }

                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                this.motionX = (double)((float)(result.hitVec.x - this.posX));
                this.motionY = (double)((float)(result.hitVec.y - this.posY));
                this.motionZ = (double)((float)(result.hitVec.z - this.posZ));
                float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                this.piercedEntity = (EntityLivingBase)entity;
                this.pierceOffsetX = this.posX - entity.posX;
                this.pierceOffsetY = this.posY - entity.posY;
                this.pierceOffsetZ = this.posZ - entity.posZ;
            }
            else
            {
                this.motionX = (double)((float)(result.hitVec.x - this.posX));
                this.motionY = (double)((float)(result.hitVec.y - this.posY));
                this.motionZ = (double)((float)(result.hitVec.z - this.posZ));
                float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                this.ticksInAir = 0;
                this.piercedEntity = (EntityLivingBase)entity;
                this.pierceOffsetX = this.posX - entity.posX;
                this.pierceOffsetY = this.posY - entity.posY;
                this.pierceOffsetZ = this.posZ - entity.posZ;
            }
        }
        else
        {
            BlockPos blockpos = result.getBlockPos();
            this.xTile = blockpos.getX();
            this.yTile = blockpos.getY();
            this.zTile = blockpos.getZ();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            this.inTile = iblockstate.getBlock();
            this.inData = this.inTile.getMetaFromState(iblockstate);
            this.motionX = (double)((float)(result.hitVec.x - this.posX));
            this.motionY = (double)((float)(result.hitVec.y - this.posY));
            this.motionZ = (double)((float)(result.hitVec.z - this.posZ));
            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.arrowShake = 7;
            this.setIsCritical(false);

            if (iblockstate.getMaterial() != Material.AIR)
            {
                this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
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
                        0.0D,
                        Block.getIdFromBlock(world.getBlockState(result.getBlockPos()).getBlock())
                );
            }
        }
    }

    public void recoverDamage(float recoveryDamage)
    {
        if (piercedEntity != null)
        {
            DamageSource damagesource;

            if (this.shootingEntity == null)
            {
                damagesource = eternalSpear.causeDamage(this);
            }
            else
            {
                damagesource = eternalSpear.causeDamage(shootingEntity);
            }

            piercedEntity.attackEntityFrom(damagesource, recoveryDamage);
        }
    }
}
