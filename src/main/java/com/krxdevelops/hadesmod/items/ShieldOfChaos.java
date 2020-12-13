package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.aegis.Aegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegisProvider;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import javax.annotation.Nullable;
import java.util.List;

public class ShieldOfChaos extends Item implements IHasModel
{
    private float attackDamage;
    private float attackSpeed;

    public ShieldOfChaos(String name, float attackDamage, float attackSpeed)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        maxStackSize = 1;
        setMaxDamage(-1);
        this.attackDamage = attackDamage + 3.0F;
        this.attackSpeed = attackSpeed;

        addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter()
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
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return false;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
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
        IAegis capability = stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
        capability.setChargingState(true);
        capability.setTicksWhenStartedCharging(worldIn.getTotalWorldTime());
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        IAegis capability = stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
        if (capability.getChargingState() && capability.isAbleToThrow(worldIn.getTotalWorldTime()))
        {
            if (!worldIn.isRemote)
            {
                EntityShieldOfChaos entityShield = new EntityShieldOfChaos(worldIn, (EntityPlayer)entityIn);
                entityShield.shoot(entityIn, entityIn.rotationPitch, entityIn.rotationYaw, 0.0F, 0.5F, 0.0F);
                worldIn.spawnEntity(entityShield);

                ((EntityPlayer)entityIn).resetActiveHand();
            }
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        IAegis capability = stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
        capability.setChargingState(false);
        capability.setTicksWhenStartedCharging(-1);
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null).getChargingState();
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        IAegis capability = stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
        double ticksPassed = Minecraft.getMinecraft().world.getTotalWorldTime() - capability.getTicksWhenStartedCharging();
        return ticksPassed > 60 ? 0.0D : 1 - ticksPassed / 60;
    }

    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 0x00FF0000;
    }

    @Override
    public void registerModels() { HadesMod.proxy.registerItemRenderer(this, 0, "inventory"); }
}
