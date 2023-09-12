package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import fr.yan36.westerlife.server.DiscordWebhook;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.DatabaseManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemCard extends Item implements IHasModel {

    public enum CardType {
        CNI(new ResourceLocation(Main.MODID, "textures/hud/cards/cni.png")),
        DIPLO(new ResourceLocation(Main.MODID, "textures/hud/cards/diplo.png")),
        GDI(new ResourceLocation(Main.MODID, "textures/hud/cards/gdi.png")),
        GOUV(new ResourceLocation(Main.MODID, "textures/hud/cards/gouv.png")),
        PERM(new ResourceLocation(Main.MODID, "textures/hud/cards/perm.png")),
        PREF(new ResourceLocation(Main.MODID, "textures/hud/cards/pref.png"));

        private ResourceLocation resourceLocation;

        CardType(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public ResourceLocation getResourceLocation() {
            return resourceLocation;
        }

    }

    private CardType type;

    public ItemCard(String name, CardType type)
    {
        setRegistryName(name);
        ItemInit.ITEMS.add(this);
        this.type = type;
        setTranslationKey(name);
    }


    @Nullable
    @Override
    public CreativeTabs getCreativeTab() {
        return Main.WESTER_CARDS;
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(worldIn != null) {
            if(!worldIn.isRemote) {
                if(stack.hasTagCompound()) {
                    if(stack.getTagCompound().hasKey("link")) {
                        tooltip.add("§aCarte synchronisée au profil de : " + Objects.requireNonNull(DBUtils.getCharacter(UUID.fromString(stack.getTagCompound().getString("link")))).getLastName());
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        System.out.println("onCreated");
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            if(!stack.hasTagCompound()) {
                stack.setTagCompound(stack.serializeNBT());
            }
            if(!stack.getTagCompound().hasKey("link")) {
                stack.getTagCompound().setString("link", String.valueOf(playerIn.getUniqueID()));
                String a = this.getType().name().substring(0, 3) + Math.round(Float.parseFloat(Math.random() * 10000000 + ""));
                stack.getTagCompound().setString("uniqueIdentifier", String.valueOf(a));
                playerIn.sendMessage(new TextComponentString("§aCarte synchronisée le profil de : " + DBUtils.getCharacter(playerIn.getUniqueID()).getLastName() + " !"));
                DiscordWebhook webhook = new DiscordWebhook(DatabaseManager.discordLogger);

                webhook.addEmbed(
                        new DiscordWebhook.EmbedObject()
                                .setTitle("Mise en circulation d'une carte")
                                .setColor(new Color(0x00FF00))
                                .setFooter("WesterLife - logger", "https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96")
                                .addField("Type de carte", this.getType().name(), true)
                                .addField("Activé par", playerIn.getUniqueID() + " " + playerIn.getName(), true)
                                .addField("Identifiant unique carte", String.valueOf(a), true)
                );

                webhook.setAvatarUrl("https://cdn.discordapp.com/icons/813796868537581588/a424290da4df55b736153d20e46f6770.webp?size=96");
                webhook.setUsername("WesterLife - logger");
                try {
                    webhook.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                playerIn.sendMessage(new TextComponentString("§cCette carte est déjà synchronisée ça serait trop facile de la voler sinon :)"));
            }

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public CardType getType() {
        return type;
    }
}
