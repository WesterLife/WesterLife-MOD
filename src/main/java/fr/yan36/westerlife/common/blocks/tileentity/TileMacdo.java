package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.yan36.westerlife.common.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TileMacdo extends TileEntitySyncClient implements ITickable {

    public enum burger {
        BREAD(1, "Pain de base", ItemInit.burger_bread, "bread"),
        STEAK(2, "Steak", ItemInit.cooked_steak, "steak"),
        SALAD(4, "Salade", ItemInit.salad, "salad"),
        TOMATO(8, "Tomate", ItemInit.tomatos, "tomato"),
        CHEESE(16, "Fromage", ItemInit.cheese, "cheese"),
        BACON(32, "Bacon", ItemInit.bacon, "bacon"),
        KETCHUP(128, "Ketchup", ItemInit.ketchup, "notrendered"),
        MAYO(256, "Mayonnaise", ItemInit.mayo, "notrendered"),
        DELUXE(512, "Deluxe", ItemInit.deluxe, "notrendered"),
        BAGUETTE(2048, "Baguette", ItemInit.baguette, "baguette"),
        CHICKEN(4096, "Poulet", ItemInit.chicken, "chiken"),
        ;

        private final int value;
        private final String displayString;
        private final Item associated_item;
        private final String rendervalue;

        private burger(int value, String displayString, Item associated_item, String rendervalue) {
            this.value = value;
            this.displayString = displayString;
            this.associated_item = associated_item;
            this.rendervalue = rendervalue;
        }

        public int getValue() {
            return value;
        }

        public String getDisplayString() {
            return displayString;
        }

        public Item getAssociated_item() {
            return associated_item;
        }

        public String getRendervalue() {
            return rendervalue;
        }

        @Override
        public String toString() {
            return this.name();
        }

    }

    private BlockObject b;

    private int steakstate = 0;
    private List<burger> burgeringredients = new ArrayList<>();

    public TileMacdo() {
        super(null);
    }

    public TileMacdo(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.steakstate = tagCompound.getInteger("steakstate");
        System.out.println(tagCompound.getString("burgeringredients").split(",").length);
        if (tagCompound.getString("burgeringredients").split(",").length == 1) {
            this.burgeringredients = new ArrayList<>();
        } else
            this.burgeringredients = Arrays.stream(tagCompound.getString("burgeringredients").split(",")).map(burger::valueOf).collect(Collectors.toList());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("steakstate", this.steakstate);
        StringBuilder sb = new StringBuilder();
        for (burger b : this.burgeringredients) {
            sb.append(b.toString()).append(",");
        }
        tagCompound.setString("burgeringredients", sb.toString());
        return tagCompound;
    }


    public void setSteakstate(int steakstate) {
        this.steakstate = steakstate;
        sync();
        markDirty();
    }

    public int getSteakstate() {
        return this.steakstate;
    }


    public void setBurgeringredients(List<burger> burgeringredients) {
        this.burgeringredients = burgeringredients;
        sync();
        markDirty();
    }

    public void addBurgeringredient(List<burger> b) {
        List<burger> temp = new ArrayList<>();
        temp.addAll(this.burgeringredients);
        temp.addAll(b);
        this.burgeringredients.clear();
        this.burgeringredients = temp;
        sync();
        markDirty();
    }

    public List<burger> getBurgeringredients() {
        if(this.burgeringredients == null) {
            this.burgeringredients = new ArrayList<>();
        }
        return this.burgeringredients;
    }

    @Override
    public void update() {

    }
}
