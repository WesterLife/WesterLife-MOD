package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.CSSGuiAtm;
import fr.yan36.westerlife.client.gui.gendarmerie.CSSGuiGendarmerieLogin;
import fr.yan36.westerlife.client.gui.pompier.CSSGuiPompierLogin;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketLoginPompier implements IMessage {

    private Boolean result;

    public PacketLoginPompier() {}

    public PacketLoginPompier(Boolean result) {
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

    public static class Handler implements IMessageHandler<PacketLoginPompier, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketLoginPompier m, MessageContext ctx) {
            if(m.result) {
                ACsGuiApi.asyncLoadThenShowGui("pompier", CSSGuiAtm::new);
                System.out.println("Login success");
            } else {
                CSSGuiPompierLogin.errorText = "Identifiant ou mot de passe incorrects.";
                System.out.println("Login failed");
            }
            return null;
        }
    }
}
