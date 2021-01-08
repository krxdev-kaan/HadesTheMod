package com.krxdevelops.hadesmod.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AdamantRail extends Item implements IHasModel
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

    @Override
    public void registerModels() {
        HadesMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
