package fr.gabidut76.westerlife.common.objects;

import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

import java.util.List;

public class CarDealer implements ISerializablePacket {
    public String name;
    public String id;
    public List<CarDealerValue> values;

    public CarDealer() {
    }

    public CarDealer(String name, String id, List<CarDealerValue> values) {
        this.name = name;
        this.id = id;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CarDealerValue> getValues() {
        return values;
    }

    public void setValues(List<CarDealerValue> values) {
        this.values = values;
    }

    public static class CarDealerValue implements ISerializablePacket {
        public String name;
        public String price;
        public String mcItem;
        public List<Integer> variants;


        public CarDealerValue() {
        }

        public CarDealerValue(String name, String price, String mcItem, List<Integer> variants) {
            this.name = name;
            this.price = price;
            this.mcItem = mcItem;
            this.variants = variants;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMcItem() {
            return mcItem;
        }

        public void setMcItem(String mcItem) {
            this.mcItem = mcItem;
        }

        public List<Integer> getVariants() {
            return variants;
        }

        public void setVariants(List<Integer> variants) {
            this.variants = variants;
        }

        @Override
        public Object[] getObjectsToSave() {
            return new Object[] { name, price, mcItem, variants };
        }

        @Override
        public void populateWithSavedObjects(Object[] objects) {
            name = (String) objects[0];
            price = (String) objects[1];
            mcItem = (String) objects[2];
            variants = (List<Integer>) objects[3];
        }
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{name, id, values};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        name = (String) objects[0];
        id = (String) objects[1];
        values = (List<CarDealerValue>) objects[2];
    }
}
