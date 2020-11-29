package com.krxdevelops.hadesmod.objects.items;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class StygiusSword extends ItemSword implements IHasModel
{
    public StygiusSword(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() { HadesMod.proxy.registerItemRenderer(this, 0, "inventory"); }
}
