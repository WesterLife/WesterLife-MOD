package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.sync.PacketAnimationToAll;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.server.Serveur;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAnimation implements IMessage{


    int id;

    public PacketAnimation(){}

    public PacketAnimation(int id) {

        this.id = id;

    }
    public PacketAnimation(Animation animation) {

        this.id = animation.getId();

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.id, 5);
    }

    public static class Handler implements IMessageHandler<PacketAnimation, IMessage> {
        @Override
        public IMessage onMessage(PacketAnimation m, MessageContext ctx) {
            System.out.println("Animation " + m.id + " received from " + ctx.getServerHandler().player.getName());
            if(!Serveur.menottes.containsKey(ctx.getServerHandler().player)) Main.network.sendToAll(new PacketAnimationToAll(m.id, ctx.getServerHandler().player.getEntityId()));

            return null;
        }
    }
}