package com.krxdevelops.hadesmod.items;

import com.krxdevelops.hadesmod.Globals;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.exagryph.CapabilityExagryph;
import com.krxdevelops.hadesmod.capabilities.exagryph.IExagryph;
import com.krxdevelops.hadesmod.entities.EntityExagryphBullet;
import com.krxdevelops.hadesmod.entities.EntityExagryphRocket;
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

public class DebugItem extends Item implements IHasModel {
    public DebugItem(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        maxStackSize = 1;

        ItemInit.ITEMS.add(this);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn != EnumHand.MAIN_HAND)
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);

        if (!worldIn.isRemote)
        {
            if (!playerIn.onGround)
            {
                Globals.isModeAdd = !Globals.isModeAdd;
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }
            else if (playerIn.isSneaking())
            {
                Globals.selectorXYZ += Globals.selectorXYZ == 3 ? -2 : 1;
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }

            if (Globals.isModeAdd)
            {
                switch (Globals.selectorXYZ)
                {
                    case 1:
                        Globals.twinFistSlimX += 0.01f;
                        break;
                    case 2:
                        Globals.twinFistSlimY += 0.01f;
                        break;
                    case 3:
                        Globals.twinFistSlimZ += 0.01;
                        break;
                }
            }
            else
            {
                switch (Globals.selectorXYZ)
                {
                    case 1:
                        Globals.twinFistSlimX -= 0.01f;
                        break;
                    case 2:
                        Globals.twinFistSlimY -= 0.01f;
                        break;
                    case 3:
                        Globals.twinFistSlimZ -= 0.01;
                        break;
                }
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void registerModels()
    {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
