package fr.yan36.westerlife.common.network;

import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.nathanael2611.simpledatabasemanager.core.SyncedDatabases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.objects.ArmorSuperpositionState;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.server.Serveur;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.*;
import java.util.stream.Collectors;

public class PacketSyncClothes implements IMessage{


    String uuid;
    String clothes;
    String action;
    byte activeID;
    int slot;

    public PacketSyncClothes(){}

    public PacketSyncClothes(String uuid, String clothes, String action, byte activeID, int slot) {
        this.uuid = uuid;
        this.clothes = clothes;
        this.action = action;
        this.activeID = activeID;
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = ByteBufUtils.readUTF8String(buf);
        this.clothes = ByteBufUtils.readUTF8String(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
        this.activeID = buf.readByte();
        this.slot = ByteBufUtils.readVarInt(buf, 5);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.uuid);
        ByteBufUtils.writeUTF8String(buf, this.clothes);
        ByteBufUtils.writeUTF8String(buf, this.action);
        buf.writeByte(this.activeID);
        ByteBufUtils.writeVarInt(buf, this.slot, 5);
    }

    public static class Handler implements IMessageHandler<PacketSyncClothes, IMessage> {
        @Override
//        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketSyncClothes m, MessageContext ctx) {
            List<String> clothes2;
            if(Main.wl_db.getString(m.uuid) == null) {
                Main.wl_db.setString(m.uuid, "");
                clothes2 = new ArrayList<>();
            } else {
                List<String> t = Arrays.asList(Main.wl_db.getString(m.uuid).split(","));
                clothes2 = new ArrayList<>(t);
            }

            int byteToInt = m.activeID & 0xFF;
            switch (m.action) {
                case "add":
                    clothes2.add(m.clothes + "!" + byteToInt + "!" + m.slot);
                    break;
                case "remove":
                    clothes2.remove(m.clothes + "!" + byteToInt + "!" + m.slot);
                    break;
                case "removeall":
                    clothes2.clear();
                    break;
            }
            Main.wl_db.setString(m.uuid, String.join(",", clothes2));

            SyncedDatabases.syncAll();

            return null;
        }
    }
}