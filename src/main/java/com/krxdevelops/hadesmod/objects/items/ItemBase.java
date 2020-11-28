package com.krxdevelops.hadesmod.objects.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.proxy.ClientProxy;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
