package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class StygiusSword extends Item implements IHasModel
{
    public float attackDamage;
    public float attackSpeed;

    public StygiusSword(String name, float attackDamage, float attackSpeed)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        maxStackSize = 1;
        setMaxDamage(-1);
        this.attackDamage = attackDamage + 3.0F;
        this.attackSpeed = attackSpeed;

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
    public void registerModels() { HadesMod.proxy.registerItemRenderer(this, 0, "inventory"); }
}
