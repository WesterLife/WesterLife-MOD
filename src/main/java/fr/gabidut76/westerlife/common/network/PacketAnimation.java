package fr.gabidut76.westerlife.common.network;

import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.sync.PacketAnimationToAll;
import fr.gabidut76.westerlife.common.utils.Animation;
import fr.gabidut76.westerlife.server.Serveur;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

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
            if(!Serveur.menottes.containsKey(ctx.getServerHandler().player)) {
                Objects.requireNonNull(ctx.getServerHandler().player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).setAnimation(Animation.getAnimationById(m.id));
                PlayerStatHandler.sync(ctx.getServerHandler().player);
            }

            return null;
        }
    }
}