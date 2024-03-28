package fr.gabidut76.westerlife.common.items;

import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.character.Permis;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import fr.gabidut76.westerlife.server.DiscordWebhook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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

    public ItemCard(String name, CardType type) {
        setRegistryName(Main.MODID, name);
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
        if (worldIn != null) {
            if (!worldIn.isRemote) {
                if (stack.hasTagCompound()) {
                    if (stack.getTagCompound().hasKey("link")) {
//                        tooltip.add("§aCarte synchronisée au profil de : " + Objects.requireNonNull(DBUtils.getCharacter(UUID.fromString(stack.getTagCompound().getString("link")))).getLastName());
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(stack.serializeNBT());
            }
            if (!stack.getTagCompound().hasKey("link")) {

                Character pchar = null;
                try {
                    pchar = NemesisLink.NEMESIS_API.getCharacterByUserUUID(playerIn.getUniqueID());
                } catch (IOException e) {
                    playerIn.sendMessage(new TextComponentString("§cImpossible de synchroniser la carte, veuillez réessayer plus tard."));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                Permis permis = null;

                try {
                    permis = NemesisLink.NEMESIS_API.getPermisByUUID(playerIn.getUniqueID());
                } catch (IOException e) {
                    playerIn.sendMessage(new TextComponentString("§cImpossible de synchroniser la carte, veuillez réessayer plus tard."));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                NBTTagCompound a = new NBTTagCompound();
                a.setString("firstname", pchar.getFirstNames());
                a.setString("lastname", pchar.getLastName());
                a.setString("gender", pchar.getGender().name());
                a.setString("nationality", pchar.getNationality());
                a.setString("birthplace", pchar.getBirthPlace());
                a.setString("birthdate", pchar.getBirthDate());
                a.setString("uniqueIdentifier", "WEST" + UUID.randomUUID().toString().substring(0, 6));
                a.setString("ownerName", playerIn.getName());
                List<Permis.PermisType> permisTypes = permis.getType();
                StringBuilder permisString = new StringBuilder();
                for (Permis.PermisType permisType : permisTypes) {
                    permisString.append(permisType.name()).append(",");
                }
                System.out.println(permisString.toString());
                a.setString("permis", permisString.toString());




                stack.getTagCompound().setTag("link", a);



                NemesisLink.NEMESIS_API.logDiscordData("1173346685011894332", "Mise en cirulation d'une carte : " + playerIn.getUniqueID() + " " + playerIn.getName() + " " + a + " " + this.getType().name());


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
