package fr.yan36.westerlife.client.renderer;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.network.PacketRequestCharacter;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientHUD {



    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Pre event) {
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Post event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(!(player == null)) {
            if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemCard) {
                ItemCard card = (ItemCard) player.getHeldItem(EnumHand.MAIN_HAND).getItem();
                GlStateManager.enableAlpha();
                Minecraft.getMinecraft().getTextureManager().bindTexture(card.getType().getResourceLocation());
                Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 256, 256, 165, 110, 256, 256);
                GlStateManager.disableAlpha();
                if (player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound() != null) {
                    if (card.getType().equals(ItemCard.CardType.CNI)) {
                        if (Client.knowCharacters.containsKey(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")))) {
                            Character target = Client.knowCharacters.get(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")));
                            if (target != null) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(0.5, 0.5, 0.5);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getLastName(), 135, 65, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getFirstNames(), 135, 90, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getGender() == Character.Gender.MALE ? "M" : "F", 135, 110, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(Objects.equals(target.getNationality(), "Française") ? "FRA" : "AUT", 170, 110, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getBirthPlace(), 235, 110, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getBirthDate(), 135, 125, 0x050505);

                                if(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                    Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 140, 0x050505);
                                } else {
                                    Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 135, 140, 0x050505);
                                }
                                Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                                GlStateManager.popMatrix();

                            } else {
//                                System.out.println("null");
                            }
                        } else {
                            if (!Client.waitForSomething.containsKey("cni")) {
                                Main.network.sendToServer(new PacketRequestCharacter(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                                Client.waitForSomething.put("cni", true);
                            }
                        }
                    } else if (card.getType().equals(ItemCard.CardType.PERM)) {
                        if (Client.knowPermis.containsKey(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))) && Client.knowCharacters.containsKey(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")))) {
                            Permis target = Client.knowPermis.get(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")));
                            Character target2 = Client.knowCharacters.get(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")));
                            if (target != null) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(0.5, 0.5, 0.5);
                                Minecraft.getMinecraft().fontRenderer.drawString(target2.getLastName(), 135, 65, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target2.getFirstNames(), 135, 85, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target2.getBirthPlace(), 135, 105, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getType().stream().map(Permis.PermisType::getLetterName).collect(Collectors.joining(", ")), 135, 125, 0x050505);

                                if(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                    Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 155, 0x050505);
                                } else {
                                    Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                                }

                                Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                                GlStateManager.popMatrix();

                            } else {
                                System.out.println("null");
                            }
                        } else {
                            if (!Client.waitForSomething.containsKey("permis")) {
                                System.out.println("send req for permis");
                                Main.network.sendToServer(new PacketRequestCharacter(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                                Client.waitForSomething.put("permis", true);
                            }
                        }
                    } else if (card.getType().equals(ItemCard.CardType.DIPLO)) {
                        if (Client.knowCharacters.containsKey(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")))) {
                            Character target = Client.knowCharacters.get(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")));
                            if (target != null) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(0.5, 0.5, 0.5);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getLastName(), 130, 65, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getFirstNames(), 130, 100, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getBirthPlace(), 130, 130, 0x050505);
                                if(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                    Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 130, 150, 0x050505);
                                } else {
                                    Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                                }

                                Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                                GlStateManager.popMatrix();

                            } else {
//                                System.out.println("null");
                            }
                        } else {
                            if (!Client.waitForSomething.containsKey("cni")) {
                                Main.network.sendToServer(new PacketRequestCharacter(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                                Client.waitForSomething.put("cni", true);
                            }
                        }
                    } else if (card.getType().equals(ItemCard.CardType.GDI) || card.getType().equals(ItemCard.CardType.PREF) || card.getType().equals(ItemCard.CardType.GOUV)) {
                        if (Client.knowCharacters.containsKey(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")))) {
                            Character target = Client.knowCharacters.get(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link")));
                            if (target != null) {
                                GlStateManager.pushMatrix();
                                GlStateManager.scale(0.5, 0.5, 0.5);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getLastName(), 135, 65, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getFirstNames(), 135, 85, 0x050505);
                                Minecraft.getMinecraft().fontRenderer.drawString(target.getBirthPlace(), 135, 105, 0x050505);
                                if(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("uniqueIdentifier")) {
                                    Minecraft.getMinecraft().fontRenderer.drawString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("uniqueIdentifier"), 135, 125, 0x050505);
                                } else {
                                    Minecraft.getMinecraft().fontRenderer.drawString("WESTX48ADEZ", 130, 150, 0x050505);
                                }

                                Minecraft.getMinecraft().fontRenderer.drawString("2030-01-01", 235, 155, 0x050505);
                                GlStateManager.popMatrix();

                            } else {
//                                System.out.println("null");
                            }
                        } else {
                            if (!Client.waitForSomething.containsKey("cni")) {
                                Main.network.sendToServer(new PacketRequestCharacter(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                                Client.waitForSomething.put("cni", true);
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println(player.getPrimaryHand());
        }



        ScaledResolution scaledresolution = event.getResolution();
        if(Databases.getPlayerData(Minecraft.getMinecraft().player).contains("notification")) {
            String notification = Databases.getPlayerData(Minecraft.getMinecraft().player).getString("notification");

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/notification.png"));
            Gui.drawScaledCustomSizeModalRect(scaledresolution.getScaledWidth() - 150, 50, 0, 0, 15, 70, 13, 70, 15, 70);

            System.out.println(notification);
        }

        if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
            GL11.glColor4f(1, 1, 1, 1);
            int width = event.getResolution().getScaledWidth();
            int x = width - 100;

            if (!Minecraft.getMinecraft().player.capabilities.disableDamage) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_empty.png"));
                Gui.drawScaledCustomSizeModalRect(40, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percent = (int) (Minecraft.getMinecraft().player.getHealth() * 70 / Minecraft.getMinecraft().player.getMaxHealth());
                if (percent > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_full.png"));
                    Gui.drawScaledCustomSizeModalRect( 40, scaledresolution.getScaledHeight() - 76 + (70 - percent), 0, 70 - percent, 15, percent, 13, percent, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_empty.png"));
                Gui.drawScaledCustomSizeModalRect(10, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentFood = (int) (Minecraft.getMinecraft().player.getFoodStats().getFoodLevel() * 70 / 20);
                if (percentFood > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_full.png"));
                    Gui.drawScaledCustomSizeModalRect( 10, scaledresolution.getScaledHeight() - 76 + (70 - percentFood), 0, 70 - percentFood, 15, percentFood, 13, percentFood, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_empty.png"));
                Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentArmor = Math.round(ClientDatabases.getPersonalPlayerData().getFloat("watervalue") * 70 / 100);

                if (percentArmor > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_full.png"));
                    Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76 + (70 - percentArmor), 0, 70 - percentArmor, 15, percentArmor, 13, percentArmor, 15, 70);
                }
            }
        }
    }
}
