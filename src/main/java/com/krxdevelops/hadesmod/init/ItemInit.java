package com.krxdevelops.hadesmod.init;

import com.krxdevelops.hadesmod.objects.items.ItemBase;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item stygianMatter = new ItemBase("ore_Stygian");
    public static final Item stygianIngot = new ItemBase("ingot_Stygian");
    public static final Item darkness = new ItemBase("darkness");
    public static final Item chthonicKey = new ItemBase("chtonicKey");
    public static final Item titanblood = new ItemBase("titanblood");
}
