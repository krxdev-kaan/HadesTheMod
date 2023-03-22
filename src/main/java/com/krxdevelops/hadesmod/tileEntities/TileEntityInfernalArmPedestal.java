package com.krxdevelops.hadesmod.tileEntities;

import com.krxdevelops.hadesmod.init.ItemInit;
import com.krxdevelops.hadesmod.util.IInfernalArm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityInfernalArmPedestal extends TileEntity {
    public NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.itemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.itemStacks);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, itemStacks);

        return compound;
    }

    public boolean interactPlayer(EntityPlayer playerIn, boolean activatedByPunch)
    {
        if (activatedByPunch)
            return takeTitanbloodFromPedestal(playerIn);
        else if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() == ItemInit.titanblood && !this.itemStacks.get(0).isEmpty())
            return putTitanbloodToPedestal(playerIn);
        else if (playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
            return takeInfernalArmFromPedestal(playerIn);
        else if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IInfernalArm)
            return putInfernalArmToPedestal(playerIn);
        else
            return false;
    }

    private boolean putTitanbloodToPedestal(EntityPlayer playerIn)
    {
        if (this.itemStacks.get(1).isEmpty())
        {
            this.itemStacks.set(1, new ItemStack(ItemInit.titanblood, 1));
            playerIn.getHeldItem(EnumHand.MAIN_HAND).setCount(playerIn.getHeldItem(EnumHand.MAIN_HAND).getCount() - 1);
        }
        else if (this.itemStacks.get(1).getCount() < 5)
        {
            this.itemStacks.get(1).setCount(this.itemStacks.get(1).getCount() + 1);
            playerIn.getHeldItem(EnumHand.MAIN_HAND).setCount(playerIn.getHeldItem(EnumHand.MAIN_HAND).getCount() - 1);
        }

        this.syncClientsToTileEntity();

        return true;
    }

    private boolean takeTitanbloodFromPedestal(EntityPlayer playerIn)
    {
        if (this.itemStacks.get(1).getCount() > 0)
        {
            this.itemStacks.get(1).setCount(this.itemStacks.get(1).getCount() - 1);
            playerIn.inventory.addItemStackToInventory(new ItemStack(ItemInit.titanblood, 1));
        }

        this.syncClientsToTileEntity();

        return true;
    }

    public boolean putInfernalArmToPedestal (EntityPlayer playerIn)
    {
        if (this.itemStacks.get(0).isEmpty())
        {
            this.itemStacks.set(0, playerIn.getHeldItem(EnumHand.MAIN_HAND));
            playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            this.syncClientsToTileEntity();
        }
        return true;
    }

    public boolean takeInfernalArmFromPedestal (EntityPlayer playerIn)
    {
        if (playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && !this.itemStacks.get(0).isEmpty())
        {
            playerIn.setHeldItem(EnumHand.MAIN_HAND, this.itemStacks.get(0));
            this.itemStacks.set(0, ItemStack.EMPTY);

            if (!this.itemStacks.get(1).isEmpty())
            {
                playerIn.inventory.addItemStackToInventory(new ItemStack(ItemInit.titanblood, this.itemStacks.get(1).getCount()));
                this.itemStacks.get(1).setCount(0);
            }

            this.syncClientsToTileEntity();
        }
        return true;
    }

    public void syncClientsToTileEntity()
    {
        for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
            player.connection.sendPacket(this.getUpdatePacket());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }
}
