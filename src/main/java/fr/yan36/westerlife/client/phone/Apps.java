package fr.yan36.westerlife.client.phone;

import fr.yan36.westerlife.client.phone.apps.AppManager;
import fr.yan36.westerlife.client.phone.apps.AppSMS;
import fr.yan36.westerlife.client.phone.apps.AppSettings;
import fr.yan36.westerlife.client.phone.apps.AppSoutMessage;
import fr.yan36.westerlife.client.phone.apps.AppGendarme;

import java.util.ArrayList;
import java.util.List;

public class Apps {
    public static List<App> APPS = new ArrayList<>();

    public static AppSettings settings = new AppSettings();
    public static AppManager appManager = new AppManager();
    public static AppSoutMessage appDev = new AppSoutMessage();
    public static AppSMS appSms = new AppSMS();

    public static AppGendarme appGendarme = new AppGendarme();


    public static void Init() {
        APPS.forEach(app -> {
            System.out.println("App: " + app.getAppName() + " " + app.getVersion());
        });
    }
}
