package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketDepoArgentServer;
import fr.yan36.westerlife.common.network.PacketRetirerArgentServer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class CSSGuiAtm extends GuiFrame {
    String status = "home";

    public CSSGuiAtm() {

        super(new GuiScaler.Identity());

        GuiPanel home = new GuiPanel();
        home.setCssClass("home");
        GuiTextArea mont;
        GuiPanel solde = new GuiPanel();
        solde.setCssClass("solde");
        solde.add(new GuiLabel(0,0,0,0, "" + Profil.getBank() + "€").setCssId("sold"));
        home.add(solde);
        GuiPanel depo = new GuiPanel();
        GuiPanel reti = new GuiPanel();
        depo.setCssClass("depo");
        depo.setCssId("depo");
        GuiPanel cinqeuros = new GuiPanel();
        cinqeuros.setCssClass("cinqeuros");
        cinqeuros.setCssId("cinqeuros");
        GuiPanel dixeuros = new GuiPanel();
        dixeuros.setCssClass("dixeuros");
        dixeuros.setCssId("dixeuros");
        GuiPanel vingteuros = new GuiPanel();
        vingteuros.setCssClass("vingteuros");
        vingteuros.setCssId("vingteuros");
        GuiPanel cinquanteeuros = new GuiPanel();
        cinquanteeuros.setCssClass("cinquanteeuros");
        cinquanteeuros.setCssId("cinquanteeuros");
        GuiPanel centeuros = new GuiPanel();
        centeuros.setCssClass("centeuros");
        centeuros.setCssId("centeuros");
        GuiPanel deuxcenteuros = new GuiPanel();
        deuxcenteuros.setCssClass("deuxcenteuros");
        deuxcenteuros.setCssId("deuxcenteuros");
        GuiPanel cinqcenteuros = new GuiPanel();
        cinqcenteuros.setCssClass("cinqcenteuros");
        cinqcenteuros.setCssId("cinqcenteuros");

        home.add(cinqeuros);
        home.add(dixeuros);
        home.add(vingteuros);
        home.add(cinquanteeuros);
        home.add(centeuros);
        home.add(deuxcenteuros);
        home.add(cinqcenteuros);
        home.add(depo);
        reti.setCssClass("reti");
        reti.setCssId("reti");
        home.add(reti);
        cinqeuros.setVisible(false);
        dixeuros.setVisible(false);
        vingteuros.setVisible(false);
        cinquanteeuros.setVisible(false);
        centeuros.setVisible(false);
        deuxcenteuros.setVisible(false);
        cinqcenteuros.setVisible(false);
        depo.addClickListener((x, y, bu) -> {
            if(cinqcenteuros.isVisible()){
            }else {
                reti.setVisible(false);
                depo.setVisible(false);
                cinqeuros.setVisible(true);
                dixeuros.setVisible(true);
                vingteuros.setVisible(true);
                cinquanteeuros.setVisible(true);
                centeuros.setVisible(true);
                deuxcenteuros.setVisible(true);
                cinqcenteuros.setVisible(true);
                solde.setVisible(false);
                depo.setCssCode("bottom: 5%;");
                status = "depo";
            }

        });
        reti.addClickListener((x, y, bu) -> {
            if(cinqcenteuros.isVisible()){

            }else {
                reti.setVisible(false);
                depo.setVisible(false);
                cinqeuros.setVisible(true);
                dixeuros.setVisible(true);
                vingteuros.setVisible(true);
                cinquanteeuros.setVisible(true);
                centeuros.setVisible(true);
                deuxcenteuros.setVisible(true);
                cinqcenteuros.setVisible(true);
                solde.setVisible(false);
                depo.setCssCode("bottom: 5%;");
                status = "reti";
            }

        });

        cinqeuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 5));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 5, new ItemStack(WesterItem.CINQEUROS)));
            }
        });

        dixeuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 10));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 10, new ItemStack(WesterItem.DIXEUROS)));
            }
        });

        vingteuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 20));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 20, new ItemStack(WesterItem.VINGTEUROS)));
            }
        });

        cinquanteeuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 50));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 50, new ItemStack(WesterItem.CINQUANTEEUROS)));
            }
        });

        centeuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 100));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 100, new ItemStack(WesterItem.CENTEUROS)));
            }
        });

        deuxcenteuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 200));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 200, new ItemStack(WesterItem.DEUXCENTEUROS)));
            }
        });

        cinqcenteuros.addClickListener((x, y, bu) -> {
            if(status.equals("reti")){
                Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 500));
            } else if (status.equals("depo")){
                Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 500, new ItemStack(WesterItem.CINQCENTEUROS)));
            }
        });
        add(home);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/atm.css"));
    }


}
