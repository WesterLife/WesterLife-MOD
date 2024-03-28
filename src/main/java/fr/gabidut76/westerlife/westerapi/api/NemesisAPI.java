package fr.gabidut76.westerlife.westerapi.api;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.gabidut76.westerlife.common.objects.CarDealer;
import fr.gabidut76.westerlife.common.objects.GarageCar;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.character.Permis;
import fr.gabidut76.westerlife.common.objects.corporations.Corporation;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.common.objects.kits.Kit;
import net.minecraft.nbt.NBTException;

import java.io.*;
import java.net.HttpURLConnection;


import java.net.URL;
import java.util.*;


public class NemesisAPI {
    private String url;
    private String key;

    public NemesisAPI(String url, String key) {
        this.url = url;
        this.key = key;
    }

    private String makeAPIRequest(String path, String method) throws IOException {
        URL url = new URL(this.url + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        // add header
        con.setRequestProperty("Authorization", NemesisLink.SERVER_API_KEY);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();


        return content.toString();
    }

    private String makeAPIRequest(String path, String method, JsonObject jsonObject) throws IOException {
        URL url = new URL(this.url + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        // add header
        con.setRequestProperty("Authorization", NemesisLink.SERVER_API_KEY);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();


        return content.toString();
    }

    public String makeTestRequest() throws IOException {

        return makeAPIRequest("", "GET");
    }

    public Character getCharacterByUserUUID(UUID uuid) throws IOException {

        String k = makeAPIRequest("user/uuid/" + uuid.toString(), "GET");

        JsonObject obj = new JsonParser().parse(k).getAsJsonObject().get("data").getAsJsonObject().get("users").getAsJsonObject();

        System.out.println(obj);
        BankAccount bankAccount = getBankAccount(BankAccount.BankAccountType.PERSONAL, uuid.toString());
        System.out.println(obj.get("_id").getAsString());
        Character character;
        if (Objects.isNull(bankAccount)) {
            character = new Character(obj.get("_id").getAsString(), uuid, obj.get("firstname").getAsString(), obj.get("lastname").getAsString(), obj.get("nationality").getAsString(), Character.Gender.getBySex(obj.get("gender").getAsString()), obj.get("birthplace").getAsString(), obj.get("birthdate").getAsString(), getPermisByUUID(uuid));
        } else {
            character = new Character(obj.get("_id").getAsString(), uuid, obj.get("firstname").getAsString(), obj.get("lastname").getAsString(), obj.get("nationality").getAsString(), Character.Gender.getBySex(obj.get("gender").getAsString()), obj.get("birthplace").getAsString(), obj.get("birthdate").getAsString(), getPermisByUUID(uuid), getBankAccount(BankAccount.BankAccountType.PERSONAL, uuid.toString()));

        }

        character.setId(obj.get("_id").getAsString());
        return character;

    }

    public List<Character> getAllCharacters() throws IOException {
        String k = makeAPIRequest("user/query/all" , "GET");

        JsonArray objs = new JsonParser().parse(k).getAsJsonObject().get("data").getAsJsonObject().get("users").getAsJsonArray();

        List<Character> characters = new ArrayList<>();
        for(JsonElement obja : objs) {
            UUID uuid = UUID.fromString(obja.getAsJsonObject().get("uuid").getAsString());
            JsonObject obj = obja.getAsJsonObject();
            BankAccount bankAccount = getBankAccount(BankAccount.BankAccountType.PERSONAL, uuid.toString());
            if (Objects.isNull(bankAccount)) {
                characters.add(new Character(obj.get("_id").getAsString(), uuid, obj.get("firstname").getAsString(), obj.get("lastname").getAsString(), obj.get("nationality").getAsString(), Character.Gender.getBySex(obj.get("gender").getAsString()), obj.get("birthplace").getAsString(), obj.get("birthdate").getAsString(), getPermisByUUID(uuid)));
            } else {
                characters.add( new Character(obj.get("_id").getAsString(), uuid, obj.get("firstname").getAsString(), obj.get("lastname").getAsString(), obj.get("nationality").getAsString(), Character.Gender.getBySex(obj.get("gender").getAsString()), obj.get("birthplace").getAsString(), obj.get("birthdate").getAsString(), getPermisByUUID(uuid), getBankAccount(BankAccount.BankAccountType.PERSONAL, uuid.toString())));

            }
        }
        return characters;
    }

    public Permis getPermisByUUID(UUID uuid) throws IOException {
        String res = makeAPIRequest("permis/uuid/" + uuid.toString(), "GET");
        JsonObject obj;
        try {
            obj = new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();
        } catch (IndexOutOfBoundsException e) {
            return new Permis(uuid, Collections.emptyList(), "0", "null", "null");
        }


        List<Permis.PermisType> types = new ArrayList<>();

        for (JsonElement a : obj.get("permis").getAsJsonArray()) {
            types.add(Permis.PermisType.valueOf(a.getAsString()));
        }

        return new Permis(
                uuid,
                types,
                obj.get("points").getAsString(),
                obj.get("obtentionDate").getAsString(),
                obj.get("delivranceAutorite").getAsString()
        );
    }

    public void makeTransaction(String from, String to, String amount) throws IOException {
        makeAPIRequest("bankaccount/transaction/" + from + "/" + to + "/" + amount, "POST");
    }

    public void changePassword(String id, String password) throws IOException {
        System.out.println("bankaccount/changepassword/" + id + "/" + password);
        makeAPIRequest("bankaccount/changepassword/" + id + "/" + password, "POST");
    }

    public List<BankAccount> getAllBankAccounts() throws IOException {
        String res = makeAPIRequest("bankaccount/rib/all", "GET");

        JsonArray objs = new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray();

        List<BankAccount> bankAccounts = new ArrayList<>();

        for (JsonElement a : objs) {
            JsonObject obj = a.getAsJsonObject();
            String _id = obj.get("_id").getAsString();
            String money = String.valueOf(obj.get("money").getAsInt());
            BankAccount.BankAccountType type = BankAccount.BankAccountType.valueOf(obj.get("ownertype").getAsString());
            String owner = obj.get("owner").getAsString();
            String rib = obj.get("rib").getAsString();
            String accountcode = String.valueOf(obj.get("accountcode").getAsInt());
            int creationDate = obj.get("creationdate").getAsInt();

            bankAccounts.add(new BankAccount(
                    _id,
                    money,
                    type,
                    owner,
                    rib,
                    accountcode,
                    creationDate
            ));
        }

        return bankAccounts;
    }

    public BankAccount getBankAccountAny(String id) throws IOException {
        String res = makeAPIRequest("bankaccount/rib/" + id, "GET");

        if (new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray().size() == 0) {
            System.out.println("No bank account found for " + id);
            return null;
        }

        JsonObject obj = new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();

        System.out.println(obj);

        String _id = obj.get("_id").getAsString();
        String money = String.valueOf(obj.get("money").getAsInt());
        BankAccount.BankAccountType type = BankAccount.BankAccountType.valueOf(obj.get("ownertype").getAsString());
        String owner = obj.get("owner").getAsString();
        String rib = obj.get("rib").getAsString();
        String accountcode = String.valueOf(obj.get("accountcode").getAsInt());
        int creationDate = obj.get("creationdate").getAsInt();


        BankAccount bankAccount = new BankAccount(
                _id,
                money,
                type,
                owner,
                rib,
                accountcode,
                creationDate

        );

        System.out.println(bankAccount.toString());

        return bankAccount;
    }

    public BankAccount getBankAccount(BankAccount.BankAccountType queryZone, String id) throws IOException {
        String res = makeAPIRequest("bankaccount/" + (queryZone.equals(BankAccount.BankAccountType.ORGANIZATION) ? "query" : "owner") + "/" + id, "GET");

        if (new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray().size() == 0) {
            System.out.println("No bank account found for " + id);
            return null;
        }

        JsonObject obj = new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject();

        String _id = obj.get("_id").getAsString();
        String money = String.valueOf(obj.get("money").getAsInt());
        BankAccount.BankAccountType type = BankAccount.BankAccountType.valueOf(obj.get("ownertype").getAsString());
        String owner = obj.get("owner").getAsString();
        String rib = obj.get("rib").getAsString();
        String accountcode = String.valueOf(obj.get("accountcode").getAsInt());
        int creationDate = obj.get("creationdate").getAsInt();


        BankAccount bankAccount = new BankAccount(
                _id,
                money,
                type,
                owner,
                rib,
                accountcode,
                creationDate

        );

        System.out.println(bankAccount.toString());

        return bankAccount;
    }


    public Corporation getCorporationByIDAsync(String id) throws IOException {
        String res = makeAPIRequest("corporation/query/" + id, "GET");
        System.out.println(res);
        JsonObject obj = new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject();

        List<Character> linkedCharacters = new ArrayList<>();

        for (JsonElement a : obj.get("linkedMembers").getAsJsonArray()) {
            String uuid = a.getAsString();
            Character c = getCharacterByUserUUID(UUID.fromString(uuid));
            linkedCharacters.add(c);
        }

        List<Corporation.EmployeeType> employeeTypes = new ArrayList<>();

        for (JsonElement a : obj.get("employeesTypes").getAsJsonArray()) {
            List<String> relativeArmors = new ArrayList<>();

            for (JsonElement b : a.getAsJsonObject().get("relativeArmors").getAsJsonArray()) {
                relativeArmors.add(b.getAsString());
            }

            List<String> relativeWeapons = new ArrayList<>();

            for (JsonElement b : a.getAsJsonObject().get("relativeWeapons").getAsJsonArray()) {
                relativeWeapons.add(b.getAsString());
            }


            employeeTypes.add(new Corporation.EmployeeType(
                    a.getAsJsonObject().get("name").getAsString(),
                    a.getAsJsonObject().get("functions").getAsString(),
                    a.getAsJsonObject().get("primes").getAsString(),
                    a.getAsJsonObject().get("salaire").getAsString(),
                    a.getAsJsonObject().get("position").getAsInt(),
                    a.getAsJsonObject().get("hasRelativeArmor").getAsBoolean(),
                    a.getAsJsonObject().get("hasRelativeWeapon").getAsBoolean(),
                    relativeArmors,
                    relativeWeapons
            ));
        }

        List<String> societyCars = new ArrayList<>();

        for (JsonElement a : obj.get("societyCars").getAsJsonArray()) {
            societyCars.add(a.getAsString());
        }

        List<String> impots = new ArrayList<>();

        for (JsonElement a : obj.get("impots").getAsJsonArray()) {
            impots.add(a.getAsString());
        }

        Corporation corp = new Corporation(
                obj.get("_id").getAsString(),
                obj.get("siret").getAsString(),
                obj.get("description").getAsString(),
                getCharacterByUserUUID(UUID.fromString(obj.get("owner").getAsString())),
                Corporation.PrimaryTypes.valueOf(obj.get("primaryType").getAsString()),
                Corporation.SubTypes.valueOf(obj.get("subType").getAsString()),
                obj.get("name").getAsString(),
                linkedCharacters,
                employeeTypes,
                societyCars,
                obj.get("capital").getAsFloat(),
                impots,
                obj.get("initialCapital").getAsFloat(),
                obj.get("relatedBankAccount").getAsString()
        );

        System.out.println(corp.toJson());

        return corp;
    }

    public void logDiscordData(String channelID, String messageContent) {
        try {
            messageContent = Base64.getEncoder().encodeToString(messageContent.getBytes());
            makeAPIRequest("discord/logsomething/b64/" + channelID + "/" + messageContent, "POST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CarDealer getCarDealerByID(String concessID) throws IOException {
        String res = makeAPIRequest("concess/query/" + concessID, "GET");
        List<CarDealer.CarDealerValue> values = new ArrayList<>();
        int i = 0;
        for (JsonElement jsonElement : new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject().get("cars").getAsJsonArray()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            ArrayList<Integer> variants = new ArrayList<>();
            for (JsonElement jsonElement1 : obj.get("variants").getAsJsonArray()) {
                variants.add(jsonElement1.getAsInt());
            }

            values.add(new CarDealer.CarDealerValue(
                    obj.get("name").getAsString(),
                    obj.get("price").getAsString(),
                    obj.get("mcname").getAsString(),
                    variants
            ));
            i++;
        }

        return new CarDealer(
                new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject().get("concessName").getAsString(),
                new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject().get("_id").getAsString(),
                values
        );
    }

    public String getUserIdFromUUID(String uuid) throws IOException {
        String res = makeAPIRequest("user/uuid/" + uuid, "GET");
        return new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject().get("users").getAsJsonObject().get("_id").getAsString();
    }

    public void addCarToGarage(String ownerID, GarageCar car) throws IOException {

        System.out.println(car.toJson().toString());

        makeAPIRequest("garage/add/" + ownerID, "POST", car.toJson());
    }

    public List<GarageCar> getGarageCars(String ownerID) throws IOException, NBTException {
        String res = makeAPIRequest("garage/userid/" + ownerID, "GET");
        List<GarageCar> cars = new ArrayList<>();
        if (new JsonParser().parse(res).getAsJsonObject().get("data").isJsonNull()) {
            return Collections.emptyList();
        }
        for (JsonElement jsonElement : new JsonParser().parse(res).getAsJsonObject().get("data").getAsJsonObject().get("cars").getAsJsonArray()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            cars.add(GarageCar.fromJson(obj));
        }
        return cars;
    }

    public void setCarState(String ownerID, String carID, String state) throws IOException {
        makeAPIRequest("garage/toggle/" + ownerID + "/" + carID + "/" + state, "POST");
    }

    public void addWarn(String playerID, String reason) throws IOException {
//        makeAPIRequest("user/warn/" + playerID + "/" + reason, "POST", "a");
    }

    public void addKit(Kit kit) {
        try {
            makeAPIRequest("kit/add", "POST", kit.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
