package fr.yan36.westerlife.common.network.old;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.acs.gendarmerie.CSSGuiGendarmerie;
import fr.yan36.westerlife.client.gui.acs.gendarmerie.CSSGuiGendarmerieLogin;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketLoginGendarmerie implements IMessage {

    private Boolean result;

    public PacketLoginGendarmerie() {}

    public PacketLoginGendarmerie(Boolean result) {

        this.result = result;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        result = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(result);

    }

    public static class Handler implements IMessageHandler<PacketLoginGendarmerie, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketLoginGendarmerie m, MessageContext ctx) {
            if(m.result) {

                ACsGuiApi.asyncLoadThenShowGui("gendarmerie", CSSGuiGendarmerie::new);
            } else {
                CSSGuiGendarmerieLogin.errorText = "Identifiant ou mot de passe incorrects.";
                System.out.println("Login failed");
            }

            return null;
        }
    }
}
