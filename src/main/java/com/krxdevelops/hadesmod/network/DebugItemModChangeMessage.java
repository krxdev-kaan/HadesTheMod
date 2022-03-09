package com.krxdevelops.hadesmod.network;

import com.krxdevelops.hadesmod.Globals;
import com.krxdevelops.hadesmod.HadesMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DebugItemModChangeMessage implements IMessage {
    private int newMod;

    public DebugItemModChangeMessage(){}

    public DebugItemModChangeMessage(int toSend) {
        this.newMod = toSend;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(newMod);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        newMod = buf.readInt();
    }

    public static class DebugItemModChangeMessageHandler implements IMessageHandler<DebugItemModChangeMessage, IMessage> {
        public DebugItemModChangeMessageHandler(){}

        @Override
        public IMessage onMessage(DebugItemModChangeMessage message, MessageContext ctx) {
            if (ctx.side.isServer())
            {
                EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
                int mod = message.newMod;

                return new DebugItemModChangeMessage(mod);
            }

            if (ctx.side.isClient())
            {
                int mod = message.newMod;
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    Globals.selectorXYZ = mod;
                });

                return null;
            }

            return null;
        }
    }
}
