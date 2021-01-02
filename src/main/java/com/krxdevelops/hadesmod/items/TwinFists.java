package com.krxdevelops.hadesmod.items;

import com.google.common.collect.Multimap;
import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.capabilities.aegis.CapabilityAegis;
import com.krxdevelops.hadesmod.capabilities.aegis.IAegis;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TwinFists extends Item implements IHasModel
{
    public float attackDamage;
    public float attackSpeed;

    public TwinFists(String name, float attackDamage, float attackSpeed)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        maxStackSize = 1;
        setMaxDamage(-1);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;

        ItemInit.ITEMS.add(this);
    }

    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn != EnumHand.MAIN_HAND)
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);

        List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, new AxisAlignedBB((int)Math.ceil(playerIn.posX)-1 - 4,
                                                                                                        (int)Math.ceil(playerIn.posY)-1,
                                                                                                        (int)Math.ceil(playerIn.posZ)-1 - 4,
                                                                                                        (int)Math.ceil(playerIn.posX)-1 + 4,
                                                                                                        (int)Math.ceil(playerIn.posY)+1,
                                                                                                        (int)Math.ceil(playerIn.posZ)-1 + 4));

        for (Entity e : entities)
        {
            e.addVelocity(0, 1.2D, 0);
        }
        playerIn.addVelocity(0, 0.8D, 0);

        Iterable<BlockPos> iterable = BlockPos.getAllInBox( (int)Math.ceil(playerIn.posX)-1 - 4,
                                                            (int)Math.ceil(playerIn.posY),
                                                            (int)Math.ceil(playerIn.posZ)-1 - 4,
                                                            (int)Math.ceil(playerIn.posX)-1 + 4,
                                                            (int)Math.ceil(playerIn.posY),
                                                            (int)Math.ceil(playerIn.posZ)-1 + 4);

        for (BlockPos bp : iterable)
        {
            if (worldIn.getBlockState(bp).getBlock().isPassable(worldIn, bp))
            {
                worldIn.spawnParticle(EnumParticleTypes.CLOUD,
                                        bp.getX(),
                                        bp.getY(),
                                        bp.getZ(),
                                        0.0,
                                        0.0,
                                        0.0,
                                        2);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
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
