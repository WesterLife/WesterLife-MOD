package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.server.AuthSystem;
import fr.yan36.westerlife.server.Plainte;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketLoginGendarmerieServer implements IMessage {

    /**
     * TODO: Si jamais tu passes par la gabidut76, tu vérifiras ce packet car il m'énerve.
     * Bisou !
     */

    private String login, password;
    private int player;

    public PacketLoginGendarmerieServer() {}

    public PacketLoginGendarmerieServer(String login, String password, EntityPlayer player) {
        this.login = login;
        this.password = password;
        this.player = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.login = ByteBufUtils.readUTF8String(buf);
        this.password = ByteBufUtils.readUTF8String(buf);
        this.player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.login);
        ByteBufUtils.writeUTF8String(buf, this.password);
        buf.writeInt(this.player);
    }

    public static class Handler implements IMessageHandler<PacketLoginGendarmerieServer, IMessage> {
        @Override
        public IMessage onMessage(PacketLoginGendarmerieServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);

            if(AuthSystem.loginGendarmerie(m.login, m.password)) {
                Main.network.sendTo(new PacketLoginGendarmerie(true), (EntityPlayerMP) e);
            } else {
                Main.network.sendTo(new PacketLoginGendarmerie(false), (EntityPlayerMP) e);
            }
            return null;
        }
    }
}
