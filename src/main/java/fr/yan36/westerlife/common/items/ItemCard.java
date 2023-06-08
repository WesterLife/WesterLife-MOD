package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.interfaces.IHasModel;
import fr.yan36.westerlife.server.bdd.DBUtils;
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
import java.util.List;

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
        setCreativeTab(Main.WESTER_CARDS);
        ItemInit.ITEMS.add(this);
        this.type = type;
        setMaxDamage(1);
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        System.out.println("onCreated");
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            if(!stack.hasTagCompound()) {
                stack.setTagCompound(stack.serializeNBT());
            }
            if(!stack.getTagCompound().hasKey("link")) {
                stack.getTagCompound().setString("link", String.valueOf(playerIn.getUniqueID()));
                playerIn.sendMessage(new TextComponentString("§aCarte synchronisée le profil de : " + DBUtils.getCharacter(playerIn.getUniqueID()).getLastName() + " !"));
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
