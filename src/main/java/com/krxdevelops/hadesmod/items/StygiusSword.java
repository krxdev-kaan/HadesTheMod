package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.stygius.CapabilityStygius;
import com.krxdevelops.hadesmod.capabilities.stygius.IStygius;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecover;
import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import com.sun.org.apache.bcel.internal.generic.ISTORE;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class StygiusSword extends Item implements IHasModel
{
    public float attackDamage;
    public float attackSpeed;
    public float smashDamage;
    public int smashRadius;
    public double smashKnockbackMutliplier;

    public StygiusSword(String name, float attackDamage, float attackSpeed, float smashDamage, int smashRadius, double smashKnockbackMutliplier)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        maxStackSize = 1;
        setMaxDamage(-1);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.smashDamage = smashDamage;
        this.smashRadius = smashRadius;
        this.smashKnockbackMutliplier = smashKnockbackMutliplier;

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
        IStygius capability = stack.getCapability(CapabilityStygius.STYGIAN_BLADE_CAPABILITY, null);
        capability.setChargingState(true);
        capability.setTicksWhenStartedCharging(worldIn.getTotalWorldTime());
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        EntityPlayer playerIn = ((EntityPlayer)entityLiving);
        ItemStack heldStackIsMainHand = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        IStygius capability = stack.getCapability(CapabilityStygius.STYGIAN_BLADE_CAPABILITY, null);

        if (heldStackIsMainHand == stack)
        {
            if (capability.getChargingState() && capability.isAbleToSmash(worldIn.getTotalWorldTime()))
            {
                if (!worldIn.isRemote)
                {
                    playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 15, 254));
                    playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 15, 3));

                    Iterable<BlockPos> blockPosIterable = BlockPos.getAllInBox(
                            (int) Math.ceil(playerIn.posX) - 1 - smashRadius,
                            (int) Math.ceil(playerIn.posY),
                            (int) Math.ceil(playerIn.posZ) - 1 - smashRadius,
                            (int) Math.ceil(playerIn.posX) - 1 + smashRadius,
                            (int) Math.ceil(playerIn.posY),
                            (int) Math.ceil(playerIn.posZ) - 1 + smashRadius);

                    List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB(
                            (int) Math.ceil(playerIn.posX) - 1 - smashRadius,
                            (int) Math.ceil(playerIn.posY) - 1,
                            (int) Math.ceil(playerIn.posZ) - 1 - smashRadius,
                            (int) Math.ceil(playerIn.posX) - 1 + smashRadius,
                            (int) Math.ceil(playerIn.posY) + 1,
                            (int) Math.ceil(playerIn.posZ) - 1 + smashRadius));

                    for (BlockPos bp : blockPosIterable) {
                        if (worldIn.getBlockState(bp).getBlock().isPassable(worldIn, bp))
                        {
                            ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.FLAME,
                                    bp.getX(),
                                    bp.getY(),
                                    bp.getZ(),
                                    1,
                                    bp.getX() - playerIn.posX,
                                    0.05,
                                    bp.getZ() - playerIn.posZ,
                                    0.1);
                        }
                    }

                    for (Entity entity : entities)
                    {
                        entity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), smashDamage);
                        float yaw = (float) (MathHelper.atan2(playerIn.posX - entity.posX, entity.posZ - playerIn.posZ) * (180D / Math.PI));
                        yaw = MathHelper.wrapDegrees(yaw);
                        double xKnockback = -MathHelper.sin(yaw * 0.017453292F);
                        double zKnockback = MathHelper.cos(yaw * 0.017453292F);
                        entity.addVelocity(xKnockback, 0.2, zKnockback);
                    }
                }
            }
        }

        capability.setChargingState(false);
        capability.setTicksWhenStartedCharging(-1);
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.getCapability(CapabilityStygius.STYGIAN_BLADE_CAPABILITY, null).getChargingState();
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        IStygius capability = stack.getCapability(CapabilityStygius.STYGIAN_BLADE_CAPABILITY, null);
        double ticksPassed = Minecraft.getMinecraft().world.getTotalWorldTime() - capability.getTicksWhenStartedCharging();
        return ticksPassed > 15 ? 0.0D : 1 - ticksPassed / 15;
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
}
