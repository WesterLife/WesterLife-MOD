package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketLoginGendarmerieServer implements IMessage {

    String login, password;
    int player;


    public PacketLoginGendarmerieServer(String login, String password, EntityPlayer player) {
        this.login = login;
        this.password = password;
        this.player = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        login = ByteBufUtils.readUTF8String(buf);
        password = ByteBufUtils.readUTF8String(buf);
        player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, login);
        ByteBufUtils.writeUTF8String(buf, password);
        buf.writeInt(player);

    }

    public static class Handler implements IMessageHandler<PacketLoginGendarmerieServer, IMessage> {
        @Override
        public IMessage onMessage(PacketLoginGendarmerieServer m, MessageContext ctx) {
            if (m.login.equals("admin") && m.password.equals("admin")) {
                if (Side.CLIENT == ctx.side) {
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            } else {



            }

            return null;
        }
    }
}
