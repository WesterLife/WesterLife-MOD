package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.serveur.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCreatePlainte implements IMessage {

    String plaignant, contre, deposition;

    public PacketCreatePlainte() {}

    public PacketCreatePlainte(String plaignant, String contre, String deposition){

        this.plaignant = plaignant;
        this.contre = contre;
        this.deposition = deposition;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.plaignant = ByteBufUtils.readUTF8String(buf);
        this.contre = ByteBufUtils.readUTF8String(buf);
        this.deposition = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, this.plaignant);
        ByteBufUtils.writeUTF8String(buf, this.contre);
        ByteBufUtils.writeUTF8String(buf, this.deposition);

    }

    public static class Handler implements IMessageHandler<PacketCreatePlainte, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCreatePlainte m, MessageContext ctx) {
            MethodesBDD.createPlainte(m.plaignant, m.contre, m.deposition);
            return null;
        }
    }
}
