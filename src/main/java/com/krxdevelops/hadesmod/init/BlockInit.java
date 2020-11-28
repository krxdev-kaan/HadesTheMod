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

    public static final Block stygianOre = new BlockBase("stygianOre", Material.ROCK);
}
