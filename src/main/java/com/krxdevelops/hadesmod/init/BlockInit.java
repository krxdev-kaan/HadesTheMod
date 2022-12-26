package com.krxdevelops.hadesmod.init;

import com.krxdevelops.hadesmod.blocks.BlockBase;
import com.krxdevelops.hadesmod.blocks.InfernalArmPedestalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block stygianOre = new BlockBase("oreblock_stygian", Material.ROCK, 7F, "pickaxe", 3, ItemInit.stygianMatter, null);
    public static final Block stygianBlock = new BlockBase("block_stygian", Material.IRON, 10F, "pickaxe", 3, null, null);
    public static final Block tartarianBlock = new BlockBase("block_tartarus", Material.ROCK, 5F, "pickaxe", 2, null, null);
    public static final InfernalArmPedestalBlock infernalArmPedestal = new InfernalArmPedestalBlock("infernal_arm_pedestal");
}
