package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.malphon.CapabilityMalphon;
import com.krxdevelops.hadesmod.capabilities.malphon.IMalphon;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasCustomDamageSource;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class TwinFists extends Item implements IHasModel, IHasCustomDamageSource
{
    public float attackDamage;
    public float attackSpeed;
    public float uppercutDamage;
    public int uppercutRadius;

    public TwinFists(String name, float attackDamage, float attackSpeed, int uppercutRadius, float uppercutDamage)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        maxStackSize = 1;
        setMaxDamage(-1);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.uppercutRadius = uppercutRadius;
        this.uppercutDamage = uppercutDamage;

        addPropertyOverride(new ResourceLocation("charging"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

        ItemInit.ITEMS.add(this);
    }

    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn != EnumHand.MAIN_HAND)
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        IMalphon capability = stack.getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
        capability.setChargingState(true);
        capability.setTicksWhenStartedCharging(worldIn.getTotalWorldTime());
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        EntityPlayer playerIn = (EntityPlayer)entityLiving;
        IMalphon capability = stack.getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);

        if (capability.isAbleToUppercut(worldIn.getTotalWorldTime()))
        {
            if (!worldIn.isRemote)
            {
                List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(
                        (int) Math.ceil(playerIn.posX) - 1 - uppercutRadius,
                        (int) Math.ceil(playerIn.posY) - 1,
                        (int) Math.ceil(playerIn.posZ) - 1 - uppercutRadius,
                        (int) Math.ceil(playerIn.posX) - 1 + uppercutRadius,
                        (int) Math.ceil(playerIn.posY) + 1,
                        (int) Math.ceil(playerIn.posZ) - 1 + uppercutRadius));

                for (Entity e : entities)
                {
                    e.attackEntityFrom(this.causeDamage(entityLiving), uppercutDamage);
                    if (e instanceof EntityPlayer)
                    {
                        e.addVelocity(0, 1.2D, 0);
                        e.velocityChanged = true;
                    }
                    else
                    {
                        e.addVelocity(0, 1.2D, 0);
                    }
                }
                entityLiving.addVelocity(0, 0.8D, 0);
                entityLiving.velocityChanged = true;

                Iterable<BlockPos> iterable = BlockPos.getAllInBox(
                        (int) Math.ceil(playerIn.posX) - 1 - uppercutRadius,
                        (int) Math.ceil(playerIn.posY),
                        (int) Math.ceil(playerIn.posZ) - 1 - uppercutRadius,
                        (int) Math.ceil(playerIn.posX) - 1 + uppercutRadius,
                        (int) Math.ceil(playerIn.posY),
                        (int) Math.ceil(playerIn.posZ) - 1 + uppercutRadius);

                for (BlockPos bp : iterable)
                {
                    float blockAngle = (float) (MathHelper.atan2(bp.getZ() - playerIn.posZ, bp.getX() - playerIn.posX) * (180D / Math.PI));
                    blockAngle = MathHelper.wrapDegrees(blockAngle);

                    float f = -MathHelper.sin(playerIn.rotationYaw * 0.017453292F);
                    float f2 = MathHelper.cos(playerIn.rotationYaw * 0.017453292F);
                    float predictedPlayerPosX = (float) (playerIn.posX + f);
                    float predictedPlayerPosZ = (float) (playerIn.posZ + f2);
                    float playerYawFromOrigin = (float) (MathHelper.atan2(predictedPlayerPosZ - playerIn.posZ, predictedPlayerPosX - playerIn.posX) * (180D / Math.PI));
                    float upperBound = (playerYawFromOrigin + 40F) % 360.0F;
                    float lowerBound = (playerYawFromOrigin - 40F) % 360.0F;

                    if (blockAngle >= lowerBound && blockAngle <= upperBound)
                    {
                        if (worldIn.getBlockState(bp).getBlock().isPassable(worldIn, bp))
                        {
                            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.CLOUD,
                                    bp.getX(),
                                    bp.getY(),
                                    bp.getZ(),
                                    2,
                                    0.0,
                                    0.2,
                                    0.0,
                                    0.0);
                        }
                    }
                }
            }
        }

        capability.setChargingState(false);
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        IMalphon capability = stack.getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
        return capability.getChargingState();
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        IMalphon capability = stack.getCapability(CapabilityMalphon.TWIN_FISTS_CAPABILITY, null);
        double ticksPassed = Minecraft.getMinecraft().world.getTotalWorldTime() - capability.getTicksWhenStartedCharging();
        return ticksPassed > 20 ? 0.0D : 1 - ticksPassed / 20;
    }

    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 0x00AA0000;
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }

    @Override
    public void registerModels() { HadesMod.proxy.registerItemRenderer(this, 0, "inventory"); }

    @Override
    public DamageSource causeDamage(Entity entity) { return new EntityDamageSource("playerMalphonUppercut", entity); }
}
