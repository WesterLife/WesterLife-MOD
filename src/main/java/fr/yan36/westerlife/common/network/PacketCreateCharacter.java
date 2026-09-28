package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.server.DiscordWebhook;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.DatabaseManager;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;

public class PacketCreateCharacter implements IMessage{


    String familyname, firstnames, birthdate, birthplace, nationality, sex;
    int player;

    public PacketCreateCharacter(){}

    public PacketCreateCharacter(EntityPlayer player, String familyname, String firstnames, String birthdate, String birthplace, String nationality, String sex) {

        this.player = player.getEntityId();
        this.familyname = familyname;
        this.firstnames = firstnames;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
        this.nationality = nationality;
        this.sex = sex;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.familyname = ByteBufUtils.readUTF8String(buf);
        this.firstnames = ByteBufUtils.readUTF8String(buf);
        this.birthdate = ByteBufUtils.readUTF8String(buf);
        this.birthplace = ByteBufUtils.readUTF8String(buf);
        this.nationality = ByteBufUtils.readUTF8String(buf);
        this.sex = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        ByteBufUtils.writeUTF8String(buf, this.familyname);
        ByteBufUtils.writeUTF8String(buf, this.firstnames);
        ByteBufUtils.writeUTF8String(buf, this.birthdate);
        ByteBufUtils.writeUTF8String(buf, this.birthplace);
        ByteBufUtils.writeUTF8String(buf, this.nationality);
        ByteBufUtils.writeUTF8String(buf, this.sex);
    }
    public static class ServerHandler implements IMessageHandler<PacketCreateCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketCreateCharacter m, MessageContext ctx) {
            net.minecraft.entity.player.EntityPlayerMP playerMP = ctx.getServerHandler().player;
            if (playerMP == null) return null;

            playerMP.getServerWorld().addScheduledTask(() -> {
                System.out.println("Received packet from " + playerMP.getUniqueID().toString() + " to create a character: " + m.firstnames + " " + m.familyname);

                Character oldActive = fr.yan36.westerlife.server.character.PlayerCharacterManager.getActiveCharacter(playerMP);
                if (oldActive != null) {
                    fr.yan36.westerlife.server.character.PlayerCharacterManager.saveCharacterState(playerMP, oldActive);
                }

                Character newChar = DBUtils.createCharacter(playerMP, m.familyname, m.firstnames, m.birthdate, m.birthplace, m.nationality, m.sex);

                fr.yan36.westerlife.server.character.PlayerCharacterManager.setActiveCharacter(playerMP, newChar);
                fr.yan36.westerlife.server.character.PlayerCharacterManager.loadCharacterState(playerMP, newChar);

                playerMP.sendMessage(new TextComponentString("§cWesterLife §8» §aVotre personnage §e" + newChar.getFullName() + " §aa bien été créé et activé ! Bon jeu !"));
                Main.network.sendTo(new PacketSendCharacter(newChar), playerMP);

                DiscordWebhook webhook = new DiscordWebhook(DatabaseManager.discordLogger);
                webhook.addEmbed(
                        new DiscordWebhook.EmbedObject()
                                .setTitle("Création d'un personnage RP")
                                .setColor(new Color(0x00FF00))
                                .setFooter("WesterLife - logger", "https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96")
                                .addField("Compte joueur", playerMP.getUniqueID() + " (" + playerMP.getName() + ")", true)
                                .addField("UUID Personnage", newChar.getUuid().toString(), true)
                                .addField("Identité créée", m.firstnames + " " + m.familyname + " | " + m.birthdate + " à " + m.birthplace + " (" + m.nationality + ", " + m.sex + ")", false)
                );
                webhook.setAvatarUrl("https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96");
                webhook.setUsername("WesterLife - logger");
                try {
                    webhook.execute();
                } catch (Exception ignored) {}
            });
            return null;
        }
    }
}