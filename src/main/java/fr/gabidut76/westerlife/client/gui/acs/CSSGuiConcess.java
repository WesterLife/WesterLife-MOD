package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.objects.CarDealer;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

@StyleToLoad
public class CSSGuiConcess extends GuiFrame {

    public CSSGuiConcess() {
        super(null);
    }

    public CSSGuiConcess(CarDealer carDealer) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel(carDealer.name);
        title.setCssClass("title");
        background.add(title);

        setEnableDebugPanel(true);
        GuiTabbedPane tabbedPane = new GuiTabbedPane();
        tabbedPane.setCssClass("tabbedpane");
        background.add(tabbedPane);


        GuiScrollPane scrollPane = new GuiScrollPane();
        scrollPane.setCssClass("scrollpane");
        scrollPane.setLayout(new GridLayout(
                new Size.SizeValue(80, GuiConstants.ENUM_SIZE.RELATIVE),
                new Size.SizeValue(-1, GuiConstants.ENUM_SIZE.RELATIVE),
                new Size.SizeValue(1.2f, GuiConstants.ENUM_SIZE.RELATIVE),
                GridLayout.GridDirection.HORIZONTAL,
                1
        ));






        int i = 0;

        for (CarDealer.CarDealerValue value : carDealer.values) {

            GuiPanel panel = new GuiPanel();
            panel.setCssClass("panel");

            GuiPanel titlePane = new GuiPanel();
            titlePane.setCssClass("titlepane");
            panel.add(titlePane);

            GuiLabel nameLabel = new GuiLabel(value.name);
            nameLabel.setCssClass("name");
            titlePane.add(nameLabel);

            GuiPanel pricePane = new GuiPanel();
            pricePane.setCssClass("pricepane");
            titlePane.add(pricePane);

            GuiLabel priceLabel = new GuiLabel(value.price + "e");
            priceLabel.setCssClass("price");
            pricePane.add(priceLabel);
            tabbedPane.addTab(value.name, panel);

            GuiPanel carPane = new GuiPanel();
            carPane.setCssClass("carpane");


            if (DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(value.getMcItem()) != null) {
                final int[] tick = {0};
                GuiPanel carpanel = new GuiPanel() {
                    @Override
                    public void drawBackground(int mouseX, int mouseY, float partialTicks) {
                        super.drawBackground(mouseX, mouseY, partialTicks);
                        GlStateManager.pushMatrix();
                        GlStateManager.disableCull();
                        GlStateManager.translate((this.getScreenX() - this.getScaledBorderSize()) + 30, (this.getScreenY() - this.getScaledBorderSize()) + 35, 20);
                        GlStateManager.translate(Math.round(this.getParent().getWidth() / 2) - 40, 0, 0);
                        GlStateManager.scale(13, 13, 13);
                        GL11.glRotatef(180, 1, 0, 0);
                        GL11.glRotatef(5, 0, 1, 0);
                        float rot = tick[0] % 1440 / 4f;
                        GlStateManager.rotate(rot, 0, 1, 0);
                        DynamXRenderUtils.renderCar(DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(value.getMcItem()), (byte) 0);
                        GlStateManager.enableCull();
                        GlStateManager.popMatrix();
                        tick[0]++;
                    }
                };
                carpanel.setCssClass("carPanel2");
                carPane.add(carpanel);
            } else {
                GuiLabel noCar = new GuiLabel("§cAucun véhicule disponible");
                noCar.setCssClass("nocar");
                carPane.add(noCar);
            }

            panel.add(carPane);

            GuiPanel concess_details = new GuiPanel();
            concess_details.setCssClass("concess_details");
            panel.add(concess_details);

            GuiPanel buyButton = new GuiPanel();
            buyButton.setCssClass("buybutton");
            panel.add(buyButton);


            GuiPanel selectBtn = new GuiPanel();
            selectBtn.setCssClass("selectbtn");

            int finalI = i;
            selectBtn.addClickListener((mouseX, mouseY, mouseButton) -> {
                tabbedPane.selectTab(finalI);
            });
//            selectBtn.getStyle().setOffsetY(i*35);
            GuiLabel selectLabel = new GuiLabel(value.name);
            selectLabel.setCssClass("selectlabel");
            selectBtn.add(selectLabel);
            GuiPanel price = new GuiPanel();
            price.setCssClass("pricepan2");
            selectBtn.add(price);
            // if price > 1000, replace last 3 digits by "k"
            if (value.price.length() > 4) {
                String priceString = value.price.substring(0, value.price.length() - 3);
                priceString += "k";
                GuiLabel priceLabel2 = new GuiLabel(priceString);
                priceLabel2.setCssClass("price2");
                price.add(priceLabel2);

            } else {
                GuiLabel priceLabel2 = new GuiLabel(value.price + "e");
                priceLabel2.setCssClass("price2");
                price.add(priceLabel2);
            }
            scrollPane.add(selectBtn);

            tabbedPane.getTabButton(i).getStyle().setVisible(false);
            i++;
        }

        background.add(scrollPane);

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/cardealer.css"));
    }
}