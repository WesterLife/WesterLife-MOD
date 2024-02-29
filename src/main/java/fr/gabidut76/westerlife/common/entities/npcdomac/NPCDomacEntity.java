package fr.gabidut76.westerlife.common.entities.npcdomac;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileAIPoint;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.items.ItemDrink;
import fr.gabidut76.westerlife.common.items.dynamx.ItemBurger;
import fr.gabidut76.westerlife.common.items.dynamx.ItemMagicWand;
import fr.gabidut76.westerlife.common.network.PacketOpenGuiWithObject;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.common.objects.gameplay.MacdoCommand;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
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
import net.minecraft.world.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NPCDomacEntity extends EntityLiving {

    private MacdoCommand.Command insideCommand;
    private ResourceLocation texture = new ResourceLocation("westerlife:textures/entities/skins/" +  (int) (Math.random() * 11) + ".png");
    private BlockPos targetPos;
    private String commandTakeByPlayer = "";
    private long spawnedAt = 0;
    private static final DataParameter<String> STATUS = EntityDataManager.<String>createKey(NPCDomacEntity.class, DataSerializers.STRING);

    public NPCDomacEntity(World worldIn) {
        super(worldIn);
    }

    public NPCDomacEntity(World worldIn, BlockPos targetPos) {
        super(worldIn);
        this.targetPos = targetPos;
        System.out.println("2: " + targetPos);
        initEntityAI();

    }

    public NPCDomacEntity(World worldIn, MacdoCommand.Command insideCommand, ResourceLocation texture, BlockPos targetPos) {
        super(worldIn);
        this.insideCommand = insideCommand;
        this.texture = texture;
        this.targetPos = targetPos;
        System.out.println(targetPos);
        this.tasks.addTask(0, new AIMovePathFinding(this, this.targetPos));
        this.commandTakeByPlayer = "";
        this.spawnedAt = System.currentTimeMillis();
    }



    @Override
    protected void initEntityAI() { // don't use, launcher after vars def.
        this.tasks.addTask(0, new AIMovePathFinding(this, targetPos));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(STATUS, "waiting");
    }

    public void setStatusData(String status) {
        this.getDataManager().set(STATUS, status);
    }

    public String getStatusData() {
        return this.getDataManager().get(STATUS);
    }

    public MacdoCommand.Command getInsideCommand() {
        return insideCommand;
    }

    public void setInsideCommand(MacdoCommand.Command insideCommand) {
        onUpdate();
        this.insideCommand = insideCommand;
    }

    public String getCommandTakeByPlayer() {
        return commandTakeByPlayer;
    }

    public long getSpawnedAt() {
        return spawnedAt;
    }

    public void setSpawnedAt(long spawnedAt) {
        this.spawnedAt = spawnedAt;
    }

    public void setCommandTakeByPlayer(String commandTakeByPlayer) {
        this.commandTakeByPlayer = commandTakeByPlayer;
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
        this.insideCommand = command;
        this.texture = new ResourceLocation(compound.getString("texture"));
        this.targetPos = new BlockPos(compound.getInteger("targetX"), compound.getInteger("targetY"), compound.getInteger("targetZ"));
        this.commandTakeByPlayer = compound.getString("commandTakeByPlayer");
        setStatusData(compound.getString("status"));
        this.spawnedAt = compound.getLong("spawnedAt");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        System.out.println("writeEntityToNBT");
        if(this.insideCommand == null) {
            this.insideCommand = new MacdoCommand.Command();
        }
        compound.setTag("insideCommand", this.insideCommand.serializeNBT());
        compound.setString("texture", texture.toString());
        compound.setInteger("targetX", targetPos.getX());
        compound.setInteger("targetY", targetPos.getY());
        compound.setInteger("targetZ", targetPos.getZ());
        compound.setString("commandTakeByPlayer", commandTakeByPlayer);
        compound.setString("status", getStatusData());
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

    public void setTargetPos(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    public BlockPos getTargetPos() {
        return targetPos;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if(!world.isRemote) {

            if(player.getHeldItem(hand).getItem() instanceof ItemMagicWand) {
                this.setDead();
            } else if (player.getHeldItem(hand).getItem() instanceof ItemBurger && this.getStatusData().equals("ok")) {
                ItemStack stack = player.getHeldItem(hand);
                List<TileMacdo.burger> ingredients = new ArrayList<>();
                assert stack.getTagCompound() != null;
                String s = stack.getTagCompound().getString("burger");
                for (String s1 : s.split(", ")) {
                    ingredients.add(TileMacdo.burger.valueOf(s1));
                }

                for (MacdoCommand.Recipe recipe : this.getInsideCommand().recipes) {
                    if(Util.listEqualsIgnoreOrder(recipe.ingredients, ingredients)) {
                        player.sendMessage(new net.minecraft.util.text.TextComponentString("§aVous avez apporté un " + recipe.name + " pour " + recipe.price + "€."));
                        player.getHeldItem(hand).shrink(1);
                        this.getInsideCommand().recipes.remove(recipe);
                        this.setInsideCommand(this.getInsideCommand());
                    }
                }
            } else if (player.getHeldItem(hand).getItem() instanceof ItemDrink && this.getStatusData().equals("ok")) {
                if(player.getHeldItem(hand).getItem().equals(DynamXInit.WATER)) {
                    if(this.getInsideCommand().drinks.contains(DynamXInit.WATER.getRegistryName().toString())) {
                        player.sendMessage(new net.minecraft.util.text.TextComponentString("§aVous avez apporté de l'eau pour 1€."));
                        player.getHeldItem(hand).shrink(1);
                        player.inventory.markDirty();
                        this.getInsideCommand().drinks.remove(DynamXInit.WATER.getRegistryName().toString());
                        this.setInsideCommand(this.getInsideCommand());
                    }
                }
            } else {
                Main.network.sendTo(new PacketOpenGuiWithObject(0, getInsideCommand(), getStatusData()), (EntityPlayerMP) player);
            }

            if(this.getInsideCommand().drinks.isEmpty() && this.getInsideCommand().recipes.isEmpty()) {
                this.setDead();
                TileAIPoint tileAIPoint = (TileAIPoint) world.getTileEntity(this.getTargetPos());
                TileAIPoint tileAIPoint1 = (TileAIPoint) world.getTileEntity(Util.parseBlockPosFromString(tileAIPoint.getTarget()));
                Thread thread = new Thread(() -> {
                    try {
                        // wait between 20 and 40 seconds
                        Thread.sleep((long) (Math.random() * 20000 + 20000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tileAIPoint1.setRelatedEntity(null);
                });

                BankAccount from = null;
                int commandUID = (int) (Math.random() * 1000000);
                NemesisLink.NEMESIS_API.logDiscordData("1179441286365847612", "-> Commande MCDO Effectuee (uid: " + commandUID + ") Commande effectuee par " + this.commandTakeByPlayer + " pour un total de " + this.getInsideCommand().totalPrice + "e");
                try {
                    from = NemesisLink.NEMESIS_API.getBankAccount(BankAccount.BankAccountType.PERSONAL, player.getUniqueID().toString());
                    System.out.println(from.getRib());
                    NemesisLink.NEMESIS_API.makeTransaction("void", from.getRib(), String.valueOf(this.getInsideCommand().totalPrice));
                } catch (IOException e) {
                    player.sendMessage(new net.minecraft.util.text.TextComponentString("§cUne erreur est survenue lors de la transaction, veuillez contacter un membre du staff (avec le code " + commandUID + "  ."));
                    throw new RuntimeException(e);
                }



                thread.start();
                player.sendMessage(new net.minecraft.util.text.TextComponentString("§aVous avez terminé la commande pour un total de : " + this.getInsideCommand().totalPrice + "$"));
            }
        }
        return true;
    }
    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        this.insideCommand = new MacdoCommand.Command();
        this.insideCommand.deserializeNBT(nbt.getCompoundTag("insideCommand"));
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
        nbt.setTag("insideCommand", this.insideCommand.serializeNBT());
        nbt.setString("texture", this.texture.toString());
        return nbt;
    }
}
