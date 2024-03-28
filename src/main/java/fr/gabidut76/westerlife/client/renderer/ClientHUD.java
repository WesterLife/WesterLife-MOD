package fr.gabidut76.westerlife.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import fr.gabidut76.westerlife.CoreMod.types.Texture;
import fr.gabidut76.westerlife.common.items.ItemCard;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.character.Permis;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static de.javagl.jgltf.model.GltfConstants.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.*;

public class ClientHUD {

    private static final ResourceLocation DEFAULT_SKIN_TEXTURE = new ResourceLocation(Main.MODID, "textures/entities/npc/npc_dialog.png");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Pre event) {

        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            if (Minecraft.getMinecraft().isReducedDebug()) {
                return;
            }
            event.setCanceled(true);
        }
    }

    public static ITextureObject getDownloadImageSkin(ResourceLocation resourceLocationIn) {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);

        if (itextureobject == null) {
            itextureobject = new ThreadDownloadImageData((File) null,
                    "http://imgur.com/Z0pbA3P.png",
                    DEFAULT_SKIN_TEXTURE, new ImageBufferDownload());

            texturemanager.loadTexture(resourceLocationIn, itextureobject);
        }


        return (itextureobject);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Post event) {
        GlStateManager.pushMatrix();
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (!(player == null)) {

            // POLLEN

            /*if (Minecraft.getMinecraft().player.getEntityWorld().getChunk(Minecraft.getMinecraft().player.getPosition()).hasCapability(PlayerChunkRelCapability.CAPABILITY, null)) {
//                GlStateManager.enableAlpha();

                ResourceLocation pollenLocation = new ResourceLocation(Main.MODID, "textures/hud/gauge/pollenempty.png");
                ResourceLocation pollenFullLocation = new ResourceLocation(Main.MODID, "textures/hud/gauge/pollenfull.png");
                Minecraft.getMinecraft().getTextureManager().bindTexture(pollenLocation);
                Gui.drawScaledCustomSizeModalRect(5, 10, 0, 0, 256, 256, 30, 10, 256, 256);

                int percent = (int) (Objects.requireNonNull(Minecraft.getMinecraft().player.getEntityWorld().getChunk(Minecraft.getMinecraft().player.getPosition()).getCapability(PlayerChunkRelCapability.CAPABILITY, null)).getCO2() * 30 / 100);

                MC.getTextureManager().bindTexture(pollenFullLocation);
                Gui.drawScaledCustomSizeModalRect(5, 10, 0, 0, 256, 256, percent, 10, 256, 256);

//                GlStateManager.disableAlpha();


            } else {
//                GlStateManager.enableAlpha();
                ResourceLocation pollenLocation = new ResourceLocation(Main.MODID, "textures/hud/gauge/pollenerror.png");
                Minecraft.getMinecraft().getTextureManager().bindTexture(pollenLocation);
                Gui.drawScaledCustomSizeModalRect(5, 10, 0, 0, 256, 256, 30, 10, 256, 256);
//                GlStateManager.disableAlpha();
            }

            */


            if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemCard) {
                ItemCard card = (ItemCard) player.getHeldItem(EnumHand.MAIN_HAND).getItem();
                GlStateManager.enableAlpha();
                Minecraft.getMinecraft().getTextureManager().bindTexture(card.getType().getResourceLocation());
                Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 256, 256, 165, 110, 256, 256);
                GlStateManager.disableAlpha();





                if (player.getHeldItem(EnumHand.MAIN_HAND).hasTagCompound()) {


                    glEnable(GL_SCISSOR_TEST);
                    glScissor(0, (int) (Minecraft.getMinecraft().displayHeight - 300), 500, 500);


                    GlStateManager.pushMatrix();
                    GlStateManager.enableAlpha();


                    ResourceLocation resourcelocation;


                    Minecraft minecraft = Minecraft.getMinecraft();
                    resourcelocation = minecraft.getSkinManager().loadSkin(
                            new MinecraftProfileTexture(
                                    "https://mineskin.eu/download/" + player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getCompoundTag("link").getString("ownerName"),
                                    new HashMap<>()
                            ),
                            MinecraftProfileTexture.Type.SKIN
                    );
//                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().load(gameProfile);
//
//                    if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
//                        resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
//                    } else {
//                        UUID uuid = EntityPlayer.getUUID(gameProfile);
//                        resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
//                    }

                    if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
                        GL11.glBegin(GL11.GL_QUADS);
                        GL11.glColor4f(1, 1, 1, 1);
                        GL11.glTexCoord2f(0, 0);
                        GL11.glVertex3f(0, 0, 0);
                        GL11.glTexCoord2f(0, 1);
                        GL11.glVertex3f(0, 256, 0);
                        GL11.glTexCoord2f(1, 1);
                        GL11.glVertex3f(256, 256, 0);
                        GL11.glTexCoord2f(1, 0);
                        GL11.glVertex3f(256, 0, 0);

                        GL11.glEnd();
                    }



                    AbstractClientPlayer ent = new InternalFakePlayer(Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getGameProfile(), resourcelocation);

                    GlStateManager.enableColorMaterial();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) 40, (float) 130, 50.0F);
                    GlStateManager.scale((float) (-50), (float) 50, (float) 50);
                    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    // change skin of entity
                    float f = ent.renderYawOffset;
                    float f1 = ent.rotationYaw;
                    float f2 = ent.rotationPitch;
                    float f3 = ent.prevRotationYawHead;
                    float f4 = ent.rotationYawHead;
                    GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

                    ent.renderYawOffset = 0;
                    ent.rotationYaw = 0;
                    ent.rotationPitch = 0;
                    ent.limbSwing = 0;
                    ent.limbSwingAmount = 0;
                    ent.prevLimbSwingAmount = 0;
                    ent.rotationYawHead = ent.rotationYaw;
                    ent.prevRotationYawHead = ent.rotationYaw;
                    GlStateManager.translate(0.0F, 0.0F, 0.0F);
                    RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
                    rendermanager.setPlayerViewY(180.0F);
                    rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
                    ent.renderYawOffset = f;
                    ent.rotationYaw = f1;
                    ent.rotationPitch = f2;
                    ent.prevRotationYawHead = f3;
                    ent.rotationYawHead = f4;
                    GlStateManager.popMatrix();
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GlStateManager.disableTexture2D();
                    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                    glDisable(GL_SCISSOR_TEST);
                    // remove scissor


                    GlStateManager.disableAlpha();
                    GlStateManager.popMatrix();
                    if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getCompoundTag("link").getKeySet().containsAll(new ArrayList<String>() {{
                        add("lastname");
                        add("firstname");
                        add("gender");
                        add("nationality");
                        add("birthplace");
                        add("birthdate");
                    }})) {
                        NBTTagCompound tag = player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getCompoundTag("link");
                        assert tag != null;
                        if (card.getType().equals(ItemCard.CardType.CNI)) {

                            GlStateManager.pushMatrix();
                            GlStateManager.scale(0.5, 0.5, 0.5);

                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("lastname"), 135, 65, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("firstname"), 135, 90, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(Character.Gender.valueOf(tag.getString("gender")) == Character.Gender.MALE ? "M" : "F", 135, 110, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(Objects.equals(tag.getString("nationality"), "Française") ? "FRA" : "AUT", 170, 110, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("birthdate"), 235, 110, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("birthplace"), 135, 125, 0x050505);

                            if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 140, 0x050505);
                            } else {
                                Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 135, 140, 0x050505);
                            }
                            Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                            GlStateManager.popMatrix();
                        } else if (card.getType().equals(ItemCard.CardType.PERM)) {
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(0.5, 0.5, 0.5);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("lastname"), 135, 65, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("firstname"), 135, 85, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("birthdate"), 135, 105, 0x050505);
                            if (!tag.getString("permis").equals("")) {
                                List<Permis.PermisType> permisTypes = Arrays.stream(tag.getString("permis").split(",")).map(Permis.PermisType::valueOf).collect(Collectors.toList());
                                Minecraft.getMinecraft().fontRenderer.drawString(permisTypes.stream().map(Permis.PermisType::getLetterName).collect(Collectors.joining(", ")), 135, 125, 0x050505); //TODO: Pass permis
                            }

                            if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 155, 0x050505);
                            } else {
                                Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                            }

                            Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                            GlStateManager.popMatrix();
                        } else if (card.getType().equals(ItemCard.CardType.DIPLO)) {
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(0.5, 0.5, 0.5);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("lastname"), 130, 65, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("firstname"), 130, 100, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("birthdate"), 130, 130, 0x050505);
                            if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 130, 150, 0x050505);
                            } else {
                                Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                            }

                            Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                            GlStateManager.popMatrix();
                        } else if (card.getType().equals(ItemCard.CardType.GDI) || card.getType().equals(ItemCard.CardType.PREF) || card.getType().equals(ItemCard.CardType.GOUV)) {
                            GlStateManager.pushMatrix();
                            GlStateManager.scale(0.5, 0.5, 0.5);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("lastname"), 135, 65, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("firstname"), 135, 85, 0x050505);
                            Minecraft.getMinecraft().fontRenderer.drawString(tag.getString("birthdate"), 135, 105, 0x050505);
                            if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 125, 0x050505);
                            } else {
                                Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                            }

                            Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                            GlStateManager.popMatrix();

                        } else {
                            System.out.println("null");
                        }


                    }
                }
            }
        }


        ScaledResolution scaledresolution = event.getResolution();

        if (event.getType().

                equals(RenderGameOverlayEvent.ElementType.ALL)) {
            GL11.glColor4f(1, 1, 1, 1);
            int width = event.getResolution().getScaledWidth();
            int x = width - 100;

            if (!Minecraft.getMinecraft().player.capabilities.disableDamage) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_empty.png"));
                Gui.drawScaledCustomSizeModalRect(40, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percent = (int) (Minecraft.getMinecraft().player.getHealth() * 70 / Minecraft.getMinecraft().player.getMaxHealth());
                if (percent > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_full.png"));
                    Gui.drawScaledCustomSizeModalRect(40, scaledresolution.getScaledHeight() - 76 + (70 - percent), 0, 70 - percent, 15, percent, 13, percent, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_empty.png"));
                Gui.drawScaledCustomSizeModalRect(10, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentFood = (int) (Minecraft.getMinecraft().player.getFoodStats().getFoodLevel() * 70 / 20);
                if (percentFood > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_full.png"));
                    Gui.drawScaledCustomSizeModalRect(10, scaledresolution.getScaledHeight() - 76 + (70 - percentFood), 0, 70 - percentFood, 15, percentFood, 13, percentFood, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_empty.png"));
                Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentArmor = Math.round(50 * 70 / 100f);

                if (percentArmor > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_full.png"));
                    Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76 + (70 - percentArmor), 0, 70 - percentArmor, 15, percentArmor, 13, percentArmor, 15, 70);
                }
            }
        }
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }

    public static class InternalFakePlayer extends AbstractClientPlayer {

        ResourceLocation skin = DefaultPlayerSkin.getDefaultSkinLegacy();

        public InternalFakePlayer(World worldIn) {
            super(worldIn, null);
        }

        public InternalFakePlayer(World worldIn, GameProfile playerProfile, ResourceLocation skin) {
            super(worldIn, playerProfile);
            this.skin = skin;
        }

        @NotNull
        @Override
        public ResourceLocation getLocationSkin() {
            return skin;
        }
    }

}
