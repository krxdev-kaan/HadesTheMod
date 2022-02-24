package com.krxdevelops.hadesmod.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.coronacht.CapabilityCoronacht;
import com.krxdevelops.hadesmod.capabilities.coronacht.ICoronacht;
import com.krxdevelops.hadesmod.capabilities.exagryph.CapabilityExagryph;
import com.krxdevelops.hadesmod.capabilities.exagryph.IExagryph;
import com.krxdevelops.hadesmod.entities.EntityCoronachtArrow;
import com.krxdevelops.hadesmod.entities.EntityExagryphBullet;
import com.krxdevelops.hadesmod.entities.EntityExagryphRocket;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasCustomDamageSource;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.Sys;

public class AdamantRail extends Item implements IHasModel, IHasCustomDamageSource
{
    private float baseDamage;
    private float rocketDamage;

    public AdamantRail(String name, float baseDamage, float rocketDamage)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.baseDamage = baseDamage;
        this.rocketDamage = rocketDamage;

        ItemInit.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn != EnumHand.MAIN_HAND)
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);

        IExagryph capability = stack.getCapability(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, null);
        if(capability.getAmmo() > 0)
        {
            if (!playerIn.isSneaking())
            {
                if (!worldIn.isRemote)
                {
                    EntityExagryphBullet bullet = new EntityExagryphBullet(worldIn, playerIn, this);
                    bullet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 5.0F, 0.5F);
                    worldIn.spawnEntity(bullet);
                }
                capability.decreaseAmmo();

                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }
            else
            {
                if (capability.isAbleToRocket(worldIn.getTotalWorldTime()))
                {
                    if (!worldIn.isRemote)
                    {
                        EntityExagryphRocket bullet = new EntityExagryphRocket(worldIn, playerIn);
                        bullet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 0.0F);
                        worldIn.spawnEntity(bullet);
                    }

                    capability.setLastRocketTicks(worldIn.getTotalWorldTime());

                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
                }

                return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
            }
        }
        else
        {
            if (!capability.getReloadingState())
            {
                capability.setReloadingState(true);
                capability.setLastReloadTicks(worldIn.getTotalWorldTime());
            }

            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        IExagryph capability = stack.getCapability(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, null);
        if (capability.getReloadingState())
        {
            if (capability.isAbleToFulfill(worldIn.getTotalWorldTime()))
            {
                capability.setReloadingState(false);
                capability.setAmmo(20);
            }
        }
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        IExagryph capability = stack.getCapability(CapabilityExagryph.ADAMANT_RAIL_CAPABILITY, null);
        if (!capability.getReloadingState())
        {
            return 1D - (double)capability.getAmmo() / 20;
        }
        else
        {
            double ticksPassed = Minecraft.getMinecraft().world.getTotalWorldTime() - capability.getLastReloadTicks();
            return (1 - ticksPassed / 20) < 0 ? 0.0F : 1 - ticksPassed / 20;
        }
    }

    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return 0x00EFAE3E;
    }

    @Override
    public void registerModels() {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public DamageSource causeDamage(Entity entity) { return new EntityDamageSource("playerExagryph", entity); }
}
