package com.krxdevelops.hadesmod.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.varatha.recover.CapabilityVarathaRecover;
import com.krxdevelops.hadesmod.entities.EntityEternalSpear;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EternalSpearRecoverItem extends Item implements IHasModel
{
    public EternalSpearRecoverItem(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        this.maxStackSize = 1;

        ItemInit.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        ItemStack spearStack = null;
        if (!worldIn.isRemote)
        {
            spearStack = new ItemStack(ItemInit.eternalSpear, 1);
            if (stack.hasCapability(CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY, null))
            {
                EntityEternalSpear entityEternalSpear = stack.getCapability(CapabilityVarathaRecover.ETERNAL_SPEAR_RECOVER_CAPABILITY, null).getEternalSpearEntity();
                if (entityEternalSpear != null)
                {
                    entityEternalSpear.recoverDamage(4F);
                    entityEternalSpear.setDead();
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, spearStack != null ? spearStack : stack);
    }

    @Override
    public void registerModels()
    {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
