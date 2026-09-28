package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.server.character.PlayerCharacterManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class PacketSelectCharacter implements IMessage {

    private UUID characterUuid;

    public PacketSelectCharacter() {
    }

    public PacketSelectCharacter(UUID characterUuid) {
        this.characterUuid = characterUuid;
    }

    public UUID getCharacterUuid() {
        return characterUuid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String s = ByteBufUtils.readUTF8String(buf);
        try {
            this.characterUuid = UUID.fromString(s);
        } catch (Exception ex) {
            this.characterUuid = null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.characterUuid != null ? this.characterUuid.toString() : "");
    }

    public static class Handler implements IMessageHandler<PacketSelectCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketSelectCharacter m, MessageContext ctx) {
            if (ctx.side == Side.SERVER && m.characterUuid != null) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                player.getServerWorld().addScheduledTask(() -> {
                    PlayerCharacterManager.selectCharacter(player, m.characterUuid);
                });
            }
            return null;
        }
    }
}
