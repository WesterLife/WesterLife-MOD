package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCreateIdentityServer implements IMessage{


    String prenom,nom,sex,date;
    int player;

    public PacketCreateIdentityServer(){}

    public PacketCreateIdentityServer(EntityPlayer player, String nom, String prenom, String sex, String date) {

        this.player =player.getEntityId();
        this.prenom=prenom;
        this.nom=nom;
        this.sex=sex;
        this.date=date;
        System.out.println("MKKKKKKKKK: "+nom+" "+prenom+" "+sex+" "+date);
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.prenom = ByteBufUtils.readUTF8String(buf);
        this.nom = ByteBufUtils.readUTF8String(buf);
        this.sex = ByteBufUtils.readUTF8String(buf);
        this.date = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        ByteBufUtils.writeUTF8String(buf, this.nom);
        ByteBufUtils.writeUTF8String(buf, this.prenom);
        ByteBufUtils.writeUTF8String(buf, this.sex);
        ByteBufUtils.writeUTF8String(buf, this.date);
    }
    public static class ServerHandler implements IMessageHandler<PacketCreateIdentityServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCreateIdentityServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            if(!MethodesBDD.getPlayerExist(e)) {
                int nombreAleatoire = 1000 + (int)(Math.random() * ((99999 - 1000) + 1));
                String rib = "01"+ Integer.toString(nombreAleatoire);
                MethodesBDD.addplayer(e, m.prenom, m.nom, m.date, m.sex, rib);
            }
            return null;
        }
    }
}