package fr.gabidut76.westerlife.common.objects.kits;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Kit implements ISerializablePacket {
    @Getter
    @Setter
    public String name;
    @Getter
    @Setter
    public String description;
    @Getter
    @Setter
    public List<NBTTagCompound> items;
    @Getter
    @Setter
    public KitRules rule;

    @Override
    public Object[] getObjectsToSave() {
        return new Object[] {name, description, items, rule};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        name = (String) objects[0];
        description = (String) objects[1];
        items = (List<NBTTagCompound>) objects[2];
        rule = (KitRules) objects[3];
    }

    public JsonObject toJson() {
        return new Gson().toJsonTree(this).getAsJsonObject();
    }
}
