package fr.yan36.westerlife.client.gui.phone;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssIntValue;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssRelativeValue;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssStringValue;
import fr.aym.acsguis.cssengine.parsing.core.objects.CssValue;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.phone.App;
import fr.yan36.westerlife.client.phone.PhoneUtils;
import fr.yan36.westerlife.common.network.PacketUpdateTileSign;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiPhone extends GuiFrame {

    public CSSGuiPhone() {

        super(new GuiScaler.Identity());

        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");
        int itemCount = 1;
        int last = 0;
        for (App installedApp : PhoneUtils.getInstalledApps()) {
            GuiPanel appPanel = new GuiPanel();
            appPanel.setCssClass("app");
            GuiLabel appName = new GuiLabel("");
            appName.setText(installedApp.getAppName());
            appPanel.add(appName);
            System.out.println(itemCount + " is " + itemCount % 2);
            if(itemCount % 2 == 0) {
                appPanel.getStyle().setOffsetX(10);
                appPanel.getStyle().setOffsetY(last);

                appName.getStyle().setOffsetX(10);
                appName.getStyle().setOffsetY(last);
            } else {
                appPanel.getStyle().setOffsetX(70);
                appPanel.getStyle().setOffsetY(itemCount * 15);

                appName.getStyle().setOffsetX(70);
                appName.getStyle().setOffsetY(itemCount * 15);
                last = itemCount * 15;
            }
            itemCount++;

            appPanel.setCssId("app");


            appName.addClickListener((x, y, e) -> {
                System.out.println("Clicked on " + installedApp.getAppName());
                installedApp.onClick(Minecraft.getMinecraft().player);
            });

            screen.add(appPanel);
            appPanel.setCssId("app");
        }

        add(screen);



    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/phone.css"));
    }


}
