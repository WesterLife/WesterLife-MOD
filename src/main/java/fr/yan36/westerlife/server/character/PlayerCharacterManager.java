package fr.yan36.westerlife.server.character;

import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemContainer;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.network.PacketSendCharacter;
import fr.yan36.westerlife.common.objects.PlayerHealth;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.server.Serveur;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentString;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerCharacterManager {

    private static final Map<UUID, Character> activeCharacters = new ConcurrentHashMap<>();
    private static final File DATA_DIR = new File("westerlife_data/characters");

    public static Character getActiveCharacter(EntityPlayer player) {
        if (player == null) return null;
        UUID accountUuid = player.getUniqueID();
        Character active = activeCharacters.get(accountUuid);
        if (active != null) {
            return active;
        }

        List<Character> list = DBUtils.getCharactersByAccount(accountUuid);
        if (!list.isEmpty()) {
            active = list.get(0);
            activeCharacters.put(accountUuid, active);
            return active;
        }

        return null;
    }

    public static void setActiveCharacter(EntityPlayer player, Character character) {
        if (player == null || character == null) return;
        activeCharacters.put(player.getUniqueID(), character);
    }

    public static boolean selectCharacter(EntityPlayerMP player, UUID targetCharacterUuid) {
        if (player == null || targetCharacterUuid == null) return false;

        Character target = DBUtils.getCharacter(targetCharacterUuid);
        if (target == null) {
            player.sendMessage(new TextComponentString("§cCe personnage n'existe pas !"));
            return false;
        }

        UUID playerAccountUuid = player.getUniqueID();
        boolean belongs = playerAccountUuid.equals(target.getAccountUuid()) || playerAccountUuid.equals(target.getUuid());
        if (!belongs) {
            player.sendMessage(new TextComponentString("§cCe personnage ne vous appartient pas !"));
            return false;
        }

        if (Serveur.menottes.containsKey(player)) {
            player.sendMessage(new TextComponentString("§cVous ne pouvez pas changer de personnage pendant que vous êtes menotté !"));
            return false;
        }

        if (player.isDead || player.getHealth() <= 0) {
            player.sendMessage(new TextComponentString("§cVous ne pouvez pas changer de personnage en étant inconscient ou mort !"));
            return false;
        }

        Character previous = activeCharacters.get(playerAccountUuid);
        if (previous != null && previous.getUuid().equals(target.getUuid())) {
            player.sendMessage(new TextComponentString("§aVous incarnez déjà ce personnage : §e" + target.getFullName()));
            return true;
        }

        if (previous != null) {
            saveCharacterState(player, previous);
        }

        setActiveCharacter(player, target);
        loadCharacterState(player, target);

        Main.network.sendTo(new PacketSendCharacter(target), player);
        player.sendMessage(new TextComponentString("§cWesterLife §8» §aVous incarnez désormais : §e" + target.getFullName() + " §a!"));
        return true;
    }

    public static void saveCharacterState(EntityPlayer player, Character character) {
        if (player == null || character == null) return;
        try {
            if (!DATA_DIR.exists()) {
                DATA_DIR.mkdirs();
            }
            File file = new File(DATA_DIR, character.getUuid().toString() + ".dat");

            NBTTagCompound root = new NBTTagCompound();

            NBTTagList invList = new NBTTagList();
            player.inventory.writeToNBT(invList);
            root.setTag("Inventory", invList);

            if (player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                IExtraItemHandler extraHandler = player.getCapability(ExtraItemCapability.CAPABILITY, null);
                if (extraHandler instanceof ExtraItemContainer) {
                    root.setTag("ExtraItems", ((ExtraItemContainer) extraHandler).serializeNBT());
                }
            }

            if (player.getEntityData().hasKey("health")) {
                root.setString("health", player.getEntityData().getString("health"));
            }

            try {
                if (Databases.getPlayerData(player) != null && Databases.getPlayerData(player).contains("watervalue")) {
                    root.setFloat("watervalue", Databases.getPlayerData(player).getFloat("watervalue"));
                }
            } catch (Exception ignored) {}

            root.setDouble("posX", player.posX);
            root.setDouble("posY", player.posY);
            root.setDouble("posZ", player.posZ);
            root.setFloat("yaw", player.rotationYaw);
            root.setFloat("pitch", player.rotationPitch);
            root.setInteger("dimension", player.dimension);

            CompressedStreamTools.write(root, file);
        } catch (Exception e) {
            System.err.println("[WesterLife] Error saving character state for " + character.getUuid() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadCharacterState(EntityPlayerMP player, Character character) {
        if (player == null || character == null) return;
        try {
            File file = new File(DATA_DIR, character.getUuid().toString() + ".dat");

            if (file.exists()) {
                NBTTagCompound root = CompressedStreamTools.read(file);

                player.inventory.clear();
                if (root.hasKey("Inventory", 9)) {
                    player.inventory.readFromNBT(root.getTagList("Inventory", 10));
                }

                if (root.hasKey("ExtraItems") && player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                    IExtraItemHandler extraHandler = player.getCapability(ExtraItemCapability.CAPABILITY, null);
                    if (extraHandler instanceof ExtraItemContainer) {
                        ((ExtraItemContainer) extraHandler).deserializeNBT(root.getCompoundTag("ExtraItems"));
                    }
                }

                if (root.hasKey("health")) {
                    player.getEntityData().setString("health", root.getString("health"));
                }

                try {
                    if (root.hasKey("watervalue") && Databases.getPlayerData(player) != null) {
                        Databases.getPlayerData(player).setFloat("watervalue", root.getFloat("watervalue"));
                    }
                } catch (Exception ignored) {}

                if (root.hasKey("posX")) {
                    double x = root.getDouble("posX");
                    double y = root.getDouble("posY");
                    double z = root.getDouble("posZ");
                    float yaw = root.getFloat("yaw");
                    float pitch = root.getFloat("pitch");
                    int dim = root.getInteger("dimension");
                    if (dim != player.dimension) {
                        player.changeDimension(dim);
                    }
                    player.connection.setPlayerLocation(x, y, z, yaw, pitch);
                }
            } else {
                player.inventory.clear();
                giveStarterPack(player, character);
                saveCharacterState(player, character);
            }

            player.inventoryContainer.detectAndSendChanges();
        } catch (Exception e) {
            System.err.println("[WesterLife] Error loading character state for " + character.getUuid() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void giveStarterPack(EntityPlayerMP player, Character character) {
        ItemStack item = new ItemStack(ItemInit.CNI);
        item.setTagCompound(new NBTTagCompound());
        assert item.getTagCompound() != null;
        item.getTagCompound().setString("link", character.getUuid().toString());
        String a = ItemCard.CardType.CNI.name().substring(0, 3) + Math.round(Float.parseFloat(Math.random() * 10000000 + ""));
        item.getTagCompound().setString("uniqueIdentifier", String.valueOf(a));
        player.inventory.addItemStackToInventory(item);

        ItemStack water = new ItemStack(DynamXInit.WATER, 2);
        water.setStackDisplayName("§b§3Bouteille d'eau");
        player.inventory.addItemStackToInventory(water);

        ItemStack food = new ItemStack(DynamXInit.barreChoco, 5);
        food.setStackDisplayName("§b§6Barre de chocolat");
        player.inventory.addItemStackToInventory(food);

        ItemStack food2 = new ItemStack(Items.BREAD, 2);
        food2.setStackDisplayName("§b§6Pain");
        player.inventory.addItemStackToInventory(food2);

        try {
            if (Databases.getPlayerData(player) != null) {
                Databases.getPlayerData(player).setFloat("watervalue", 100f);
            }
        } catch (Exception ignored) {}
        player.getEntityData().setString("health", new PlayerHealth(Collections.emptyList(), Collections.emptyList()).toString());
    }

    public static void onPlayerLogout(EntityPlayerMP player) {
        if (player == null) return;
        UUID accountUuid = player.getUniqueID();
        Character active = activeCharacters.get(accountUuid);
        if (active != null) {
            saveCharacterState(player, active);
            activeCharacters.remove(accountUuid);
        }
    }
}
