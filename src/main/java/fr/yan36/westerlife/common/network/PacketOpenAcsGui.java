package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.client.gui.acs.*;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.objects.LightSequence;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;
import java.util.concurrent.Callable;

public class PacketOpenAcsGui implements IMessage{


    int screen;
    String args;
    String args2;

    public PacketOpenAcsGui(){}

    public PacketOpenAcsGui(Integer screen, String args, String args2) {
        this.screen = screen;
        this.args = args;
        this.args2 = args2;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.screen = ByteBufUtils.readVarInt(buf, 5);
        this.args = ByteBufUtils.readUTF8String(buf);
        this.args2 = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, this.screen, 5);
        ByteBufUtils.writeUTF8String(buf, this.args);
        ByteBufUtils.writeUTF8String(buf, this.args2);
    }

    public static class Handler implements IMessageHandler<PacketOpenAcsGui, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenAcsGui m, MessageContext ctx) {
            if(m.screen == 1) {
                ACsGuiApi.asyncLoadThenShowGui("lights", () -> new CSSGuiLights(LightSequence.fromString(m.args), m.args2));
            }
            if(m.screen == 2) {
                ACsGuiApi.asyncLoadThenShowGui("blockColor", () -> new CSSGuiColoredBlock(Util.parseBlockPosFromString(m.args)));
            }
            if(m.screen == 3) {
                ACsGuiApi.asyncLoadThenShowGui("blockColor", () -> new CSSGuiGendKit(m.args));
            }
            if(m.screen == 4) {
                ACsGuiApi.asyncLoadThenShowGui("notifgui", CSSGuiNotif::new);
            }
            if(m.screen == 5) {
                ACsGuiApi.asyncLoadThenShowGui("editObjectGui",() -> new CSSGuiEditObject(m.args, m.args2));
            }
            if(m.screen == 6) {
                ACsGuiApi.asyncLoadThenShowGui("garage",() -> new CSSGuiGarage(m.args, m.args2));
            }
            if(m.screen == 7) {
                ACsGuiApi.asyncLoadThenShowGui("macdo",() -> new CSSGuiMacdo(m.args, m.args2));
            }
            return null;
        }
    }
}