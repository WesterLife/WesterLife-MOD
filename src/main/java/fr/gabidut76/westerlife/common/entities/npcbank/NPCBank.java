package fr.gabidut76.westerlife.common.entities.npcbank;

import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileAIPoint;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.entities.npcdomac.AIMovePathFinding;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.items.ItemDrink;
import fr.gabidut76.westerlife.common.items.dynamx.ItemBurger;
import fr.gabidut76.westerlife.common.items.dynamx.ItemMagicWand;
import fr.gabidut76.westerlife.common.network.PacketOpenGuiECO;
import fr.gabidut76.westerlife.common.network.PacketOpenGuiWithObject;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.common.objects.gameplay.MacdoCommand;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NPCBank extends EntityLiving {


    private ResourceLocation texture = new ResourceLocation("westerlife:textures/entities/skins/" +  (int) (Math.random() * 11) + ".png");

    private long spawnedAt = 0;



    public NPCBank(World worldIn) {
        super(worldIn);
        initEntityAI();

    }

    public NPCBank(World worldIn, MacdoCommand.Command insideCommand, ResourceLocation texture, BlockPos targetPos) {
        super(worldIn);
        this.texture = texture;
        this.spawnedAt = System.currentTimeMillis();
    }



    @Override
    protected void initEntityAI() {
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }


    public long getSpawnedAt() {
        return spawnedAt;
    }

    public void setSpawnedAt(long spawnedAt) {
        this.spawnedAt = spawnedAt;
    }

    public ResourceLocation getTexture() {

        return this.texture;
    }

    public void setTexture(ResourceLocation texture) {
        onUpdate();
        this.texture = texture;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        System.out.println("readEntityFromNBT");
        MacdoCommand.Command command = new MacdoCommand.Command();
        System.out.println(compound.getCompoundTag("insideCommand"));
        command.deserializeNBT(compound.getCompoundTag("insideCommand"));
        this.texture = new ResourceLocation(compound.getString("texture"));
        this.spawnedAt = compound.getLong("spawnedAt");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        System.out.println("writeEntityToNBT");

        compound.setString("texture", texture.toString());
        compound.setLong("spawnedAt", spawnedAt);
    }

    @Override
    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }

    @Override
    public float getAIMoveSpeed() {
        return .5f;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public boolean getIsInvulnerable() {
        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!world.isRemote) {

            if(player.getHeldItem(hand).getItem() instanceof ItemMagicWand) {
                this.setDead();
                return  true;
            }

            List<BankAccount> gaccounts = new ArrayList<>();
            try {
                gaccounts = NemesisLink.NEMESIS_API.getAllBankAccounts();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(gaccounts == null) {
                player.sendMessage(new TextComponentString("§cErreur lors de la récupération des comptes bancaires"));
            }

            if(gaccounts.size() == 0) {
                player.sendMessage(new TextComponentString("§cAucun compte bancaire n'a été trouvé"));
            }

            List<BankAccount> accounts = new ArrayList<>();

            for (BankAccount account : gaccounts) {
                if(account.getOwner().equals(player.getUniqueID().toString())) {
                    accounts.add(account);

                }
            }



            Character c = null;
            try {
                c = NemesisLink.NEMESIS_API.getCharacterByUserUUID(player.getUniqueID());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Main.network.sendTo(new PacketOpenGuiECO(accounts, Collections.singletonList(c)), (EntityPlayerMP) player);


        }
        return true;
    }
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.texture = new ResourceLocation(nbt.getString("texture"));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagCompound temp = compound;
        temp.setBoolean("Glows", false);
        super.readFromNBT(temp);
    }

    @Override
    public void setGlowing(boolean glowingIn) {

    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setString("texture", this.texture.toString());
        return nbt;
    }
}
