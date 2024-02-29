package fr.gabidut76.westerlife.common.network.old;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncPlayer implements IMessage{

    String prenom,nom,sex,date,rib;
    Double bank;

    public PacketSyncPlayer(){}


    public PacketSyncPlayer(String nom, String prenom, String sex, String date,Double bank,String rib) {

        this.prenom=prenom;
        this.nom=nom;
        this.sex=sex;
        this.date=date;
        this.bank = bank;
        this.rib = rib;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.prenom = ByteBufUtils.readUTF8String(buf);
        this.nom = ByteBufUtils.readUTF8String(buf);
        this.sex = ByteBufUtils.readUTF8String(buf);
        this.date = ByteBufUtils.readUTF8String(buf);
        this.bank = buf.readDouble();
        this.rib = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.nom);
        ByteBufUtils.writeUTF8String(buf, this.prenom);
        ByteBufUtils.writeUTF8String(buf, this.sex);
        ByteBufUtils.writeUTF8String(buf, this.date);
        buf.writeDouble(this.bank);
        ByteBufUtils.writeUTF8String(buf, this.rib);
    }

    public static class Handler implements IMessageHandler<PacketSyncPlayer, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSyncPlayer m, MessageContext ctx) {
            return null;
        }
    }
}