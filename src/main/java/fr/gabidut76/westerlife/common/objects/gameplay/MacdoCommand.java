package fr.gabidut76.westerlife.common.objects.gameplay;

import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MacdoCommand {

    public static class Recipe implements INBTSerializable<NBTBase>, ISerializablePacket {
        public List<TileMacdo.burger> ingredients;
        public String name;
        public int price;

        public Recipe() {
        }

        public Recipe(List<TileMacdo.burger> ingredients, String name, int price) {
            this.ingredients = ingredients;
            this.name = name;
            this.price = price;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("name", name);
            nbt.setInteger("price", price);
            NBTTagList ingredientsNBT = new NBTTagList();
            for (TileMacdo.burger ingredient : ingredients) {
                ingredientsNBT.appendTag(ingredient.serializeNBT());
            }
            nbt.setTag("ingredients", ingredientsNBT);

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            name = tag.getString("name");
            price = tag.getInteger("price");
            NBTTagList ingredientsNBT = tag.getTagList("ingredients", 10);
            ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsNBT.tagCount(); i++) {
                NBTTagCompound ingredientNBT = (NBTTagCompound) ingredientsNBT.get(i);
                System.out.println(ingredientNBT.getKeySet());
                TileMacdo.burger ingredient = TileMacdo.burger.valueOf(ingredientNBT.getString("name"));
                ingredient.deserializeNBT(ingredientsNBT.get(i));
                ingredients.add(ingredient);
            }
        }

        @Override
        public Object[] getObjectsToSave() {
            return new Object[] { ingredients, name, price };
        }

        @Override
        public void populateWithSavedObjects(Object[] objects) {
            ingredients = (List<TileMacdo.burger>) objects[0];
            name = (String) objects[1];
            price = (int) objects[2];
        }
    }

    public static List<String> DRINKS = new ArrayList<String>() {{
        add(DynamXInit.WATER.getRegistryName().toString());
    }};

    public static class Command implements INBTSerializable<NBTBase>, ISerializablePacket {
        public List<Recipe> recipes;
        public List<String> drinks;

        public int totalPrice = 0;

        public Command() {
            this.recipes = new ArrayList<>();
            this.drinks = new ArrayList<>();
        }

        public Command(List<Recipe> recipes, List<String> drinks, int totalPrice) {
            this.recipes = recipes;
            this.drinks = drinks;
            this.totalPrice = totalPrice;
        }

        @Override
        public NBTBase serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagList recipesNBT = new NBTTagList();
            for (Recipe recipe : recipes) {
                recipesNBT.appendTag(recipe.serializeNBT());
            }
            nbt.setTag("recipes", recipesNBT);
            NBTTagList drinksNBT = new NBTTagList();
            for (String drink : drinks) {
                drinksNBT.appendTag(new NBTTagString(Objects.requireNonNull(drink)));
            }
            nbt.setTag("drinks", drinksNBT);

            return nbt;
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            this.recipes = new ArrayList<>();
            this.drinks = new ArrayList<>();
            NBTTagCompound tag = (NBTTagCompound) nbt;
            NBTTagList recipesNBT = tag.getTagList("recipes", 10);
            for (int i = 0; i < recipesNBT.tagCount(); i++) {
                Recipe recipe = new Recipe(null, null, 0);
                recipe.deserializeNBT(recipesNBT.get(i));
                recipes.add(recipe);
            }
            NBTTagList drinksNBT = tag.getTagList("drinks", 8);
            for (int i = 0; i < drinksNBT.tagCount(); i++) {
                String drink = drinksNBT.getStringTagAt(i);
                drinks.add(drink);
            }

        }

        @Override
        public Object[] getObjectsToSave() {
            return new Object[] { recipes, drinks, totalPrice };
        }

        @Override
        public void populateWithSavedObjects(Object[] objects) {
            recipes = (List<Recipe>) objects[0];
            drinks = (List<String>) objects[1];
            totalPrice = (int) objects[2];
        }
    }

    public static List<Recipe> RECIPES = new ArrayList<>();

    public static void init() {
        RECIPES.add(new Recipe(new ArrayList<TileMacdo.burger>() {{
            add(TileMacdo.burger.BREAD);
            add(TileMacdo.burger.DELUXE);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.SALAD);
            add(TileMacdo.burger.STEAK);
            add(TileMacdo.burger.TOMATO);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.BREAD);
        }}, "MucCheese", 10));

        RECIPES.add(new Recipe(new ArrayList<TileMacdo.burger>() {{
            add(TileMacdo.burger.BREAD);
            add(TileMacdo.burger.DELUXE);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.SALAD);
            add(TileMacdo.burger.STEAK);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.STEAK);
            add(TileMacdo.burger.BREAD);
        }}, "TripleMuc", 11));

        RECIPES.add(new Recipe(new ArrayList<TileMacdo.burger>() {{
            add(TileMacdo.burger.BAGUETTE);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.TOMATO);
            add(TileMacdo.burger.SALAD);
            add(TileMacdo.burger.CHICKEN);
        }}, "MucBaguette", 14));

        RECIPES.add(new Recipe(new ArrayList<TileMacdo.burger>() {{
            add(TileMacdo.burger.BREAD);
            add(TileMacdo.burger.KETCHUP);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.CHICKEN);
            add(TileMacdo.burger.CHEESE);
            add(TileMacdo.burger.SALAD);
            add(TileMacdo.burger.BREAD);
        }}, "MucChicken", 12));
    }

    public static Command generateCommand() {
        List<Recipe> recipes = new ArrayList<>();
        List<String> drinks = new ArrayList<>();

        int totalPrice = 0;

        int nbRecipes = (int) (Math.random() * 3);
        for (int i = 0; i < nbRecipes; i++) {
            Recipe recipe = RECIPES.get((int) (Math.random() * RECIPES.size()));
            recipes.add(recipe);
            totalPrice += recipe.price;
        }

        int nbDrinks = (int) (Math.random() * 3) + 1;
        for (int i = 0; i < nbDrinks; i++) {
            String drink = DRINKS.get((int) (Math.random() * DRINKS.size()));
            drinks.add(drink);
            totalPrice += 1;
        }

        return new Command(recipes, drinks, totalPrice);
    }
}
