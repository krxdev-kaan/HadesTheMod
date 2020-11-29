package com.krxdevelops.hadesmod.init;

import com.krxdevelops.hadesmod.objects.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block stygianOre = new BlockBase("oreblock_Stygian", Material.ROCK, 7F, "pickaxe", 3, ItemInit.stygianMatter, null);
    public static final Block stygianBlock = new BlockBase("block_Stygian", Material.IRON, 10F, "pickaxe", 3, null, null);
    public static final Block tartarianBlock = new BlockBase("block_Tartarus", Material.ROCK, 5F, "pickaxe", 2, null, null);
}
