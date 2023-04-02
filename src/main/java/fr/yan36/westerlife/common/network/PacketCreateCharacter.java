package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCreateCharacter implements IMessage{


    String familyname, firstnames, birthdate, birthplace, nationality, sex;
    int player;

    public PacketCreateCharacter(){}

    public PacketCreateCharacter(EntityPlayer player, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex) {

        this.player = player.getEntityId();
        this.familyname = familyname;
        this.firstnames = firstnames;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.nationality = nationality;
        this.sex = sex;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.familyname = ByteBufUtils.readUTF8String(buf);
        this.firstnames = ByteBufUtils.readUTF8String(buf);
        this.birthdate = ByteBufUtils.readUTF8String(buf);
        this.birthplace = ByteBufUtils.readUTF8String(buf);
        this.nationality = ByteBufUtils.readUTF8String(buf);
        this.sex = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        ByteBufUtils.writeUTF8String(buf, this.familyname);
        ByteBufUtils.writeUTF8String(buf, this.firstnames);
        ByteBufUtils.writeUTF8String(buf, this.birthdate);
        ByteBufUtils.writeUTF8String(buf, this.birthplace);
        ByteBufUtils.writeUTF8String(buf, this.nationality);
        ByteBufUtils.writeUTF8String(buf, this.sex);
    }
    public static class ServerHandler implements IMessageHandler<PacketCreateCharacter, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCreateCharacter m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            if(Side.SERVER.isServer()) {
                if (!DBUtils.getCharacterExists(e)) {
                    DBUtils.createCharacter(e, m.familyname, m.firstnames, m.birthdate, m.birthplace, m.nationality, m.sex);
                    e.sendMessage(new TextComponentString("§cWesterLife §8» §aVotre personnage a bien été créé ! Bon jeu !"));
                }
            }
            return null;
        }
    }
}