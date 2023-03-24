package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.old.PacketUpdateTileSign;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiChangeSign extends GuiFrame {

    public CSSGuiChangeSign(String pos) {

        super(new GuiScaler.Identity());

        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");
        GuiTextArea Nom, Color;
        Nom = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(40).setHintText("Texte").setCssId("Prenom");
        Color = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(40).setHintText("Texte").setCssId("Nom");
        Color.setText("black");
        screen.add(Nom);
        screen.add(Color);

        GuiPanel confirm = new GuiPanel();
        confirm.setCssId("confirm").addClickListener((x, y, bu) -> {
            Main.network.sendToServer(new PacketUpdateTileSign(pos, Nom.getText(), Color.getText()));
        });
        screen.add(confirm);
        add(screen);

        /*

            BlockPos pos1 = Util.parseBlockPosFromString(pos);
            World world = Minecraft.getMinecraft().world;
            try {
                Objects.requireNonNull(world.getTileEntity(pos1)).getTileData().setString("text", textInput.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.RED +"An error as occured, please check the console."));
            }

         */


    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/createprofil.css"));
    }


}
