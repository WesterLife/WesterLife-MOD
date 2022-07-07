package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIAtmServer implements IMessage{

    int player;

    public PacketOpenGUIAtmServer() {

    }

    public PacketOpenGUIAtmServer(EntityPlayer player) {
        this.player = player.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(player);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIAtmServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketOpenGUIAtmServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e),MethodesBDD.getPrenom(e),MethodesBDD.getSex(e),MethodesBDD.getDate(e),MethodesBDD.getArgent(e),MethodesBDD.getRIB(e)), (EntityPlayerMP) e);
            Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e)), ctx.getServerHandler().player);
        return null;
        }
    }
}