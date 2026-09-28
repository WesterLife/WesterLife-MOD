package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketSelectCharacter;
import fr.yan36.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSCharacterSelection extends GuiFrame {

    public CSSCharacterSelection(List<Character> characters) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("Sélectionnez votre personnage");
        title.setCssClass("title");
        background.add(title);

        GuiScrollPane scrollPane = new GuiScrollPane();
        scrollPane.setCssClass("scrollPane");
        scrollPane.setLayout(
                new GridLayout(
                        new Size.SizeValue(-1, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(72, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(8, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        GridLayout.GridDirection.HORIZONTAL,
                        1
                )
        );

        if (characters == null || characters.isEmpty()) {
            GuiLabel empty = new GuiLabel("Aucun personnage trouvé. Créez-en un !");
            empty.setCssClass("emptyLabel");
            scrollPane.add(empty);
        } else {
            for (Character c : characters) {
                GuiPanel card = new GuiPanel();
                card.setCssClass("card");

                GuiLabel name = new GuiLabel(c.getFirstNames() + " " + c.getLastName());
                name.setCssClass("charName");
                card.add(name);

                String details = "Né(e) le " + c.getBirthDate() + " à " + c.getBirthPlace() + " (" + c.getNationality() + ")";
                GuiLabel info = new GuiLabel(details);
                info.setCssClass("charInfo");
                card.add(info);

                String genderStr = (c.getGender() == Character.Gender.MALE) ? "Homme" : "Femme";
                GuiLabel gender = new GuiLabel(genderStr);
                gender.setCssClass("charGender");
                card.add(gender);

                GuiButton playBtn = new GuiButton("Incarner");
                playBtn.setCssClass("playBtn");
                playBtn.addClickListener((mouseX, mouseY, mouseButton) -> {
                    Main.network.sendToServer(new PacketSelectCharacter(c.getUuid()));
                    Minecraft.getMinecraft().displayGuiScreen(null);
                });
                card.add(playBtn);

                scrollPane.add(card);
            }
        }

        background.add(scrollPane);

        GuiButton newCharBtn = new GuiButton("+ Créer un personnage");
        newCharBtn.setCssClass("newCharBtn");
        newCharBtn.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSCreateCharacter().getGuiScreen());
        });
        background.add(newCharBtn);

        GuiButton closeBtn = new GuiButton("Fermer");
        closeBtn.setCssClass("closeBtn");
        closeBtn.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });
        background.add(closeBtn);

        add(background);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/selectchar.css"));
    }
}
