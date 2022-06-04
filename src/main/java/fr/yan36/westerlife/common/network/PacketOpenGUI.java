package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.ByteBuffer;

public class PacketOpenGUI implements IMessage{

    private static int id;

    public PacketOpenGUI(int id) {
        id = this.id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, id, 5);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUI, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenGUI m, MessageContext ctx) {
            if (ctx.side.isClient()) {
                switch (m.id) {
                    case 1:
                        //ATM
                        ACsGuiApi.asyncLoadThenShowHudGui("atm", CSSGuiAtm::new);
                        break;
                    case 2:
                        break;
                }

            } else {
                EntityPlayerMP player = ctx.getServerHandler().player;
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(player),MethodesBDD.getPrenom(player),MethodesBDD.getSex(player),MethodesBDD.getDate(player),MethodesBDD.getArgent(player)), player);

            }

            return null;
        }


    }
}
