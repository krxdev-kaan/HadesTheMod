package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.ItemInit;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
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
    private int knockbackStrength;
    private double damage;

    protected EntityLivingBase piercedEntity;

    public EntityEternalSpear(World worldIn)
    {
        super(worldIn);
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.damage = 18.0D;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public EntityEternalSpear(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
    }

    public EntityEternalSpear(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
    }

    protected float getGravityVelocity()
    {
        return 0.0001F;
    }

    @Override
    public void onUpdate()
    {
        if(piercedEntity == null)
            super.onUpdate();
        else
        {

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
                damagesource = DamageSource.causeArrowDamage(this, this);
            }
            else
            {
                damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
            }

            if (entity.attackEntityFrom(damagesource, (float)this.damage))
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
            }
            else
            {
                this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                this.ticksInAir = 0;
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
}
