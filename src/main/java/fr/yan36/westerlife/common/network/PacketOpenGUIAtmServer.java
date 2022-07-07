package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIAtmServer implements IMessage{

    public PacketOpenGUIAtmServer() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketOpenGUIAtmServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketOpenGUIAtmServer m, MessageContext ctx) {
            EntityPlayerMP e = (EntityPlayerMP) ctx.getServerHandler().player;
            Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e),MethodesBDD.getPrenom(e),MethodesBDD.getSex(e),MethodesBDD.getDate(e),MethodesBDD.getArgent(e),MethodesBDD.getRIB(e)), (EntityPlayerMP) e);
            Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e)), ctx.getServerHandler().player);
        return null;
        }
    }
}