package com.krxdevelops.hadesmod.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.varatha.CapabilityVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.IVaratha;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecover;
import com.krxdevelops.hadesmod.entities.EntityCoronachtArrow;
import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class HeartSeekingBow extends Item implements IHasModel
{
    public HeartSeekingBow(String name, float initialDamage, float moderateDamage, float maxDamage)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        this.maxStackSize = 1;
        this.setMaxDamage(-1);

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return !(entityIn.getActiveItemStack().getItem() instanceof HeartSeekingBow) ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });

        ItemInit.ITEMS.add(this);
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

    public static float getArrowVelocity(int charge)
    {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        return f;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn != EnumHand.MAIN_HAND)
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        EntityPlayer playerIn = ((EntityPlayer)entityLiving);

        int i = this.getMaxItemUseDuration(stack) - timeLeft;
        if (i < 0) return;

        float f = getArrowVelocity(i);

        if ((double)f >= 0.1D)
        {
            if (!worldIn.isRemote)
            {
                EntityCoronachtArrow coronachtArrow = new EntityCoronachtArrow(worldIn, entityLiving, 15F);
                coronachtArrow.rotationPitch = playerIn.rotationPitch;
                coronachtArrow.rotationYaw = playerIn.rotationYaw;
                coronachtArrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                worldIn.spawnEntity(coronachtArrow);
            }

            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
        }
    }

    @Override
    public void registerModels() {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
