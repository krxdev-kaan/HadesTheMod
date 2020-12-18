package com.krxdevelops.hadesmod.init;

import com.krxdevelops.hadesmod.items.EternalSpear;
import com.krxdevelops.hadesmod.items.ItemBase;
import com.krxdevelops.hadesmod.items.ShieldOfChaos;
import com.krxdevelops.hadesmod.items.StygiusSword;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemInit
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item.ToolMaterial TOOL_STYGIAN = EnumHelper.addToolMaterial("tool_stygian", 0, -1, 1.0F, 11F, 14);

    public static final StygiusSword stygiusSword = new StygiusSword("arm_stygius", 15F, -2.5F);
    public static final EternalSpear eternalSpear = new EternalSpear("arm_varatha", 18F, -2.0F);
    public static final ShieldOfChaos shieldOfChaos = new ShieldOfChaos("arm_aegis", 18F, -2.9F);

    public static final Item stygianMatter = new ItemBase("ore_stygian");
    public static final Item stygianIngot = new ItemBase("ingot_stygian");
    public static final Item darkness = new ItemBase("darkness");
    public static final Item chthonicKey = new ItemBase("chthonic_key");
    public static final Item titanblood = new ItemBase("titanblood");
}
