package fr.yan36.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.acs.macdo.CSSGuiCommand;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.entities.npcdomac.NPCDomacEntity;
import fr.yan36.westerlife.common.network.sync.PacketSetEntityData;
import fr.yan36.westerlife.common.objects.gameplay.MacdoCommand;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketTakeMacdoCommand extends SerializablePacket implements IDnxPacket {



    public PacketTakeMacdoCommand() {
        super(new Object[0]);
    }

    public PacketTakeMacdoCommand(Object object) {
        super(object);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    @SideOnly(Side.SERVER)
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if(side.isServer()) {

        }
    }

    public static class ServerHandler implements IMessageHandler<PacketTakeMacdoCommand, IMessage> {
        @Override
        public IMessage onMessage(PacketTakeMacdoCommand message, MessageContext ctx) {
            Objects.requireNonNull(ctx.getServerHandler().player.getServer()).addScheduledTask(() -> {
                Entity target = Util.getEntityLookAt(ctx.getServerHandler().player, 5);
                if(target instanceof NPCDomacEntity) {
                    NPCDomacEntity npc = (NPCDomacEntity) target;
                    if(npc.getCommandTakeByPlayer().equals("")) {
                        npc.setCommandTakeByPlayer(ctx.getServerHandler().player.getName());
                        npc.setStatusData("ok");
                        npc.onUpdate();

                        Main.network.sendTo(new PacketSetEntityData(npc.getUniqueID().toString(), "status", "ok"), ctx.getServerHandler().player);

                        Main.network.sendTo(new PacketOpenAcsGui(0,"",""), ctx.getServerHandler().player);
                    }
                }
            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketTakeMacdoCommand, IMessage> {
        @Override
        public IMessage onMessage(PacketTakeMacdoCommand message, MessageContext ctx) {

            return null;
        }
    }
}
