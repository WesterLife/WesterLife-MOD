package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Profil;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketReqSyncPlayer implements IMessage{

    String prenom,nom,sex,date;
    Double bank;

    public PacketReqSyncPlayer(){}


    public PacketReqSyncPlayer(String nom, String prenom, String sex, String date, Double bank) {

        this.prenom=prenom;
        this.nom=nom;
        this.sex=sex;
        this.date=date;
        this.bank = bank;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.prenom = ByteBufUtils.readUTF8String(buf);
        this.nom = ByteBufUtils.readUTF8String(buf);
        this.sex = ByteBufUtils.readUTF8String(buf);
        this.date = ByteBufUtils.readUTF8String(buf);
        this.bank = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.nom);
        ByteBufUtils.writeUTF8String(buf, this.prenom);
        ByteBufUtils.writeUTF8String(buf, this.sex);
        ByteBufUtils.writeUTF8String(buf, this.date);
        buf.writeDouble(this.bank);
    }

    public static class Handler implements IMessageHandler<PacketReqSyncPlayer, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketReqSyncPlayer m, MessageContext ctx) {
            Profil.setDate(m.date);
            Profil.setNom(m.nom);
            Profil.setPrenom(m.prenom);
            Profil.setSex(m.sex);
            Profil.setBank(m.bank);
            return null;
        }
    }
}