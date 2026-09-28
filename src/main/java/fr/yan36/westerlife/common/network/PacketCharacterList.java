package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.gui.acs.CSSCharacterSelection;
import fr.yan36.westerlife.common.objects.character.Character;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PacketCharacterList implements IMessage {

    private List<Character> characters;

    public PacketCharacterList() {
        this.characters = new ArrayList<>();
    }

    public PacketCharacterList(List<Character> characters) {
        this.characters = characters != null ? characters : new ArrayList<>();
    }

    public List<Character> getCharacters() {
        return characters;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int count = buf.readInt();
        this.characters = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String str = ByteBufUtils.readUTF8String(buf);
            this.characters.add(Character.fromString(str));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(characters.size());
        for (Character character : characters) {
            ByteBufUtils.writeUTF8String(buf, character.toString());
        }
    }

    public static class Handler implements IMessageHandler<PacketCharacterList, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCharacterList m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Minecraft.getMinecraft().displayGuiScreen(new CSSCharacterSelection(m.characters).getGuiScreen());
            });
            return null;
        }
    }
}
