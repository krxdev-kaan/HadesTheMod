package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.client.GuiOverlay;
import com.krxdevelops.hadesmod.entities.EntityShieldOfChaos;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasCustomDamageSource;
import com.krxdevelops.hadesmod.util.IHasModel;
import com.krxdevelops.hadesmod.util.IHasOverlay;
import com.krxdevelops.hadesmod.util.IInfernalArm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ShieldOfChaos extends Item implements IHasModel, IHasCustomDamageSource, IHasOverlay, IInfernalArm
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
        this.attackDamage = attackDamage;
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
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        IAegis capability = stack.getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);

        if (capability.getChargingState() && capability.isAbleToThrow(worldIn.getTotalWorldTime()))
        {
            if (!worldIn.isRemote)
            {
                EntityShieldOfChaos entityShield = new EntityShieldOfChaos(worldIn, entityLiving, this);
                entityShield.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, 1.0F, 0.0F);
                worldIn.spawnEntity(entityShield);
            }
        }

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

    @Override
    public DamageSource causeDamage(Entity entity) { return new EntityDamageSource("playerAegisThrow", entity); }

    @Override
    public void renderOverlay(RenderGameOverlayEvent.Post event, GuiOverlay gui)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = event.getResolution();
        IAegis capability = mc.player.getHeldItem(EnumHand.MAIN_HAND).getCapability(CapabilityAegis.SHIELD_OF_CHAOS_CAPABILITIES, null);
        if (capability.getChargingState())
        {
            gui.renderChargeBar(
                    mc,
                    sc,
                    (sc.getScaledWidth() / 2) - 101,
                    sc.getScaledHeight() / 2 + 64,
                    1.0f,
                    0.0f,
                    0.0f,
                    (double)(mc.world.getTotalWorldTime() - capability.getTicksWhenStartedCharging()) + event.getPartialTicks(),
                    60.0D
            );
        }
    }
}
