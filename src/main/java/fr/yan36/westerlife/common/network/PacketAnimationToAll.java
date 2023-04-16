package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.server.Serveur;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketAnimationToAll implements IMessage{


    int id;
    int entityId;

    public PacketAnimationToAll(){}

    public PacketAnimationToAll(int id, int entityId) {
        this.entityId = entityId;
        this.id = id;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = ByteBufUtils.readVarInt(buf, 5);
        this.entityId = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.id, 5);
        ByteBufUtils.writeVarInt(buf, this.entityId, 5);
    }

    public static class Handler implements IMessageHandler<PacketAnimationToAll, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketAnimationToAll m, MessageContext ctx) {
            if(Client.animationState.containsKey(m.entityId))
                Client.animationState.replace(m.entityId, Animation.getAnimationById(m.id));
            else
                Client.animationState.put(m.entityId, Animation.getAnimationById(m.id));
            return null;
        }
    }
}