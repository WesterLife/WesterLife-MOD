package fr.gabidut76.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.gabidut76.westerlife.client.gui.acs.*;
import fr.gabidut76.westerlife.client.gui.acs.atm.CSSGuiATMHome;
import fr.gabidut76.westerlife.client.gui.acs.atm.CSSGuiATMLogin;
import fr.gabidut76.westerlife.client.gui.acs.atm.CSSGuiATMTransfer;
import fr.gabidut76.westerlife.client.gui.other.GuiCreativeInventoryCustom;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.objects.LightSequence;
import fr.gabidut76.westerlife.common.objects.character.Character;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import java.util.Arrays;

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
            if(m.screen == 0) {
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
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
            if(m.screen == 8) {
                ACsGuiApi.asyncLoadThenShowGui("debug",() -> new GuiDebugShowValues(Arrays.asList(m.args.split(";"))));
            }
            if(m.screen == 9) {
                ACsGuiApi.asyncLoadThenShowGui("admin", CSSGuiStaff::new);
            }
            if(m.screen == 10) {
                ACsGuiApi.asyncLoadThenShowGui("atm",() -> new CSSGuiATMLogin(m.args,m.args2));
            }
            if(m.screen == 11) {
                if(m.args2.split("\\$")[1].equals("corp")) {
                    ACsGuiApi.asyncLoadThenShowGui("atm_home",() -> new CSSGuiATMHome(m.args,m.args2.split("\\$")[0], null));
                    return null;
                }
                ACsGuiApi.asyncLoadThenShowGui("atm_home",() -> new CSSGuiATMHome(m.args,m.args2.split("\\$")[0], Character.fromString(m.args2.split("\\$")[1])));
            }
            if(m.screen == 12) {
                if(m.args2.split("\\$")[1].equals("corp")) {
                    ACsGuiApi.asyncLoadThenShowGui("transacok",() -> new CSSGuiATMTransfer(m.args,m.args2.split("\\$")[0], null, true));
                    return null;
                }
                ACsGuiApi.asyncLoadThenShowGui("transacoktransacok",() -> new CSSGuiATMTransfer(m.args,m.args2.split("\\$")[0], Character.fromString(m.args2.split("\\$")[1]), true));
            }
            if(m.screen == 13) {

                Mouse.destroy();

                try {
                    Mouse.create();
                } catch (LWJGLException e) {
                    throw new RuntimeException(e);
                }
                Minecraft.getMinecraft().displayGuiScreen(new GuiCreativeInventoryCustom(Minecraft.getMinecraft().player));

            }
            return null;
        }
    }
}