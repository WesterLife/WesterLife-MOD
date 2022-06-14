package fr.yan36.westerlife.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketLoginGendarmerie implements IMessage {

    int player;


    public PacketLoginGendarmerie(EntityPlayer player) {
        this.player = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(player);

    }

    public static class Handler implements IMessageHandler<PacketLoginGendarmerie, IMessage> {
        @Override
        public IMessage onMessage(PacketLoginGendarmerie m, MessageContext ctx) {



            return null;
        }
    }
}
