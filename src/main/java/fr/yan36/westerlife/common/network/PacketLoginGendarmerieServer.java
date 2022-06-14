package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.server.AuthSystem;
import fr.yan36.westerlife.server.Plainte;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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

    String login, password;

    public PacketLoginGendarmerieServer(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.login = ByteBufUtils.readUTF8String(buf);
        this.password = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.login);
        ByteBufUtils.writeUTF8String(buf, this.password);
    }

    public static class ServerHandler implements IMessageHandler<PacketLoginGendarmerieServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketLoginGendarmerieServer m, MessageContext ctx) {
            System.out.println("Login Gendarmerie Server");
            return null;
        }
    }
}
