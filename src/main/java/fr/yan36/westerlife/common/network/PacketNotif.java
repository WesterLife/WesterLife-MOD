package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.server.DiscordWebhook;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.DatabaseManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.PermissionAPI;

import java.awt.*;
import java.io.IOException;
import java.util.UUID;

public class PacketNotif implements IMessage{

    String uuid;
    String message;


    public PacketNotif() {
    }

    public PacketNotif(EntityPlayer p, String message){
        this.uuid = p.getUniqueID().toString();
        this.message = message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = ByteBufUtils.readUTF8String(buf);
        this.message = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.uuid);
        ByteBufUtils.writeUTF8String(buf, this.message);
    }
    public static class Handler implements IMessageHandler<PacketNotif, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketNotif m, MessageContext ctx) {
            if(!ctx.getServerHandler().player.canUseCommand(4, "wl.notif")) return null;
            if(!PermissionAPI.hasPermission(ctx.getServerHandler().player, "wl.notif")) return null;
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getPlayerEntityByUUID(UUID.fromString(m.uuid));
            if(Side.SERVER.isServer()) {
                assert e != null;
                String sb = "§8§m--------§f§7[§f §6WESTERLIFE§f§7 ]§8§m--------\n\n" +
                        m.message.replaceAll("&", "§") +
                        "\n\n§8§m-----------------------------";
                e.sendMessage(new TextComponentString(sb));
            }
            return null;
        }
    }
}