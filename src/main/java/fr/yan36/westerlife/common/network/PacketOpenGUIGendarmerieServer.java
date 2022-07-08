package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenGUIGendarmerieServer implements IMessage{


    private int player;

    public PacketOpenGUIGendarmerieServer() {}

    public PacketOpenGUIGendarmerieServer(EntityPlayer player) {
        this.player = player.getEntityId();
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
    }

    public static class Handler implements IMessageHandler<PacketOpenGUIGendarmerieServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketOpenGUIGendarmerieServer m, MessageContext ctx) {
            MethodesBDD.getAccountGendarme();
            MethodesBDD.getAmendes();
            MethodesBDD.getPlainte();
            MethodesBDD.getAvisDeRecherche();
            MethodesBDD.getTAJ();

             //ACsGuiApi.asyncLoadThenShowGui("gendarmerie", () -> new CSSGuiGendarmerie(myList));

            return null;
        }
    }
}
