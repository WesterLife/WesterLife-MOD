package fr.yan36.westerlife.common.network;

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
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCreateCharacter m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            if(Side.SERVER.isServer()) {
                assert e != null;
                if (!DBUtils.isRowExistInDatabase("players", "uuid", e.getUniqueID().toString())) {
                    System.out.println("Received packet from " + e.getUniqueID().toString() + " to create a character. (s=" + m.sex + ")");
                    DBUtils.saveToDB(new Character(e.getUniqueID(), m.firstnames, m.familyname, m.nationality, Character.Gender.getBySex(m.sex), m.birthdate, m.birthplace));
                    e.sendMessage(new TextComponentString("§cWesterLife §8» §aVotre personnage a bien été créé ! Bon jeu !"));
                    ItemStack item = new ItemStack(ItemInit.CNI);
                    item.setTagCompound(new NBTTagCompound());
                    assert item.getTagCompound() != null;
                    item.getTagCompound().setString("link", e.getUniqueID().toString());

                    item.getTagCompound().setString("link", String.valueOf(e.getUniqueID()));
                    String a = ItemCard.CardType.CNI.name().substring(0, 3) + Math.round(Float.parseFloat(Math.random() * 10000000 + ""));
                    item.getTagCompound().setString("uniqueIdentifier", String.valueOf(a));
                    e.sendMessage(new TextComponentString("§aCarte synchronisée le profil de : " + DBUtils.getCharacter(e.getUniqueID()).getLastName() + " !"));

                    ItemStack food = new ItemStack(Items.COOKED_BEEF, 10);
                    food.setStackDisplayName("§cDe la nourriture propre arrivera bientôt !");
                    e.inventory.addItemStackToInventory(food);

                    DiscordWebhook webhook = new DiscordWebhook(DatabaseManager.discordLogger);

                    webhook.addEmbed(
                            new DiscordWebhook.EmbedObject()
                                    .setTitle("Mise en circulation d'une carte")
                                    .setColor(new Color(0x00FF00))
                                    .setFooter("WesterLife - logger", "https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96")
                                    .addField("Type de carte", ItemCard.CardType.CNI.name(), true)
                                    .addField("Activé par", e.getUniqueID() + " " + e.getName(), true)
                                    .addField("Identifiant unique carte", String.valueOf(a), true)
                                    .addField("Lié a identitée créé", m.firstnames + " " + m.familyname + " " + m.birthdate + " " + m.birthplace + " " + m.nationality + " " + m.sex , true)
                    );

                    webhook.setAvatarUrl("https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96");
                    webhook.setUsername("WesterLife - logger");
                    try {
                        webhook.execute();
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }

                    e.inventory.addItemStackToInventory(item);

                }
            }
            return null;
        }
    }
}