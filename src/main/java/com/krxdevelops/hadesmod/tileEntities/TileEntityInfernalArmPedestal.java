package com.krxdevelops.hadesmod.tileEntities;

import com.krxdevelops.hadesmod.util.IInfernalArm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityInfernalArmPedestal extends TileEntity {
    public NonNullList<ItemStack> itemStacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.itemStacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.itemStacks);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, itemStacks);

        return compound;
    }

    public boolean interactPlayer(EntityPlayer playerIn)
    {
        if (!playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
        {
            return putInfernalArmToPedestal(playerIn);
        }
        else
        {
            return takeInfernalArmFromPedestal(playerIn);
        }
    }

    public boolean putInfernalArmToPedestal (EntityPlayer playerIn)
    {
        if (this.itemStacks.get(0).isEmpty())
        {
            if (playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IInfernalArm)
            {
                this.itemStacks.set(0, playerIn.getHeldItem(EnumHand.MAIN_HAND));
                playerIn.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                this.syncClientsToTileEntity();
            }
        }
        return true;
    }

    public boolean takeInfernalArmFromPedestal (EntityPlayer playerIn)
    {
        if (playerIn.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && !this.itemStacks.get(0).isEmpty())
        {
            playerIn.setHeldItem(EnumHand.MAIN_HAND, this.itemStacks.get(0));
            this.itemStacks.set(0, ItemStack.EMPTY);
            this.syncClientsToTileEntity();
        }
        return true;
    }

    public void syncClientsToTileEntity()
    {
        for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
        {
            player.connection.sendPacket(this.getUpdatePacket());
        }
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
