package com.krxdevelops.hadesmod.blocks;

import com.krxdevelops.hadesmod.HadesMod;
import com.krxdevelops.hadesmod.init.BlockInit;
import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.util.Random;

public class BlockBase extends Block implements IHasModel
{
    protected Item customDrop = null;

    public BlockBase(String name, Material material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockBase(String name, Material material, Float hardness, String toolClassName, Integer harvestLevel, Item customDrop, CreativeTabs customCreativeTab)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);

        if(customCreativeTab != null)
        {
            setCreativeTab(customCreativeTab);
        }
        else
        {
            setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        }

        if(customDrop != null)
        {
            this.customDrop = customDrop;
        }

        if(hardness != null)
        {
            setHardness(hardness);
        }

        if(toolClassName != null && harvestLevel != null)
        {
            setHarvestLevel(toolClassName, harvestLevel);
        }

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(customDrop != null)
        {
            return customDrop;
        }
        else
        {
            return super.getItemDropped(state, rand, fortune);
        }
    }

    @Override
    public void registerModels()
    {
        HadesMod.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
