package com.krxdevelops.hadesmod.entities;

import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.items.HeartSeekingBow;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.animation.AnimationModelBase;

import java.util.ArrayList;
import java.util.List;

public class EntityCoronachtArrow extends EntityArrow
{
    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private int inData;
    private int knockbackStrength;

    private float damageIn;
    private boolean canPierce;
    private boolean everHitTheGround = false;
    private List<Entity> piercedEntities = new ArrayList<Entity>();

    private HeartSeekingBow heartSeekingBow;

    public EntityCoronachtArrow(World worldIn)
    {
        super(worldIn);
        this.heartSeekingBow = ItemInit.heartSeekingBow;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public EntityCoronachtArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.heartSeekingBow = ItemInit.heartSeekingBow;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public EntityCoronachtArrow(World worldIn, EntityLivingBase shooter, HeartSeekingBow heartSeekingBow, float damageIn, boolean canPierce)
    {
        super(worldIn, shooter);
        this.heartSeekingBow = heartSeekingBow;
        this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
        this.damageIn = damageIn;
        this.canPierce = canPierce;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.knockbackStrength = 1;
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (!this.everHitTheGround)
        {
            this.world.spawnParticle(
                    EnumParticleTypes.SPELL,
                    this.posX,
                    this.posY,
                    this.posZ,
                    0.0D,
                    0.0D,
                    0.0D,
                    2
            );
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn)
    {
        Entity entity = raytraceResultIn.entityHit;

        if (entity != null)
        {
            if (!piercedEntities.contains(entity))
            {
                DamageSource damagesource;

                if (this.shootingEntity == null)
                {
                    damagesource = heartSeekingBow.causeDamage(this);
                }
                else
                {
                    damagesource = heartSeekingBow.causeDamage(this.shootingEntity);
                }

                if (entity.attackEntityFrom(damagesource, damageIn))
                {
                    piercedEntities.add(entity);

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

                        this.arrowHit(entitylivingbase);

                        if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                        {
                            ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                        }
                    }

                    this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                    if (!canPierce)
                        this.setDead();
                }
                else
                {
                    if (!this.world.isRemote && !canPierce)
                    {
                        this.setDead();
                    }
                }
            }
        }
        else
        {
            BlockPos blockpos = raytraceResultIn.getBlockPos();
            this.xTile = blockpos.getX();
            this.yTile = blockpos.getY();
            this.zTile = blockpos.getZ();
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            this.inTile = iblockstate.getBlock();
            this.inData = this.inTile.getMetaFromState(iblockstate);
            this.motionX = (double)((float)(raytraceResultIn.hitVec.x - this.posX));
            this.motionY = (double)((float)(raytraceResultIn.hitVec.y - this.posY));
            this.motionZ = (double)((float)(raytraceResultIn.hitVec.z - this.posZ));
            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.everHitTheGround = true;
            this.arrowShake = 7;
            this.setIsCritical(false);

            if (iblockstate.getMaterial() != Material.AIR)
            {
                this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
            }
        }
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return null;
    }
}
