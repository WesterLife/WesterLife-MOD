package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.gabidut76.westerlife.client.renderer.ClientNotifications;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.gabidut76.westerlife.common.network.kits.PacketCreateKit;
import fr.gabidut76.westerlife.common.objects.CarDealer;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import fr.gabidut76.westerlife.common.objects.kits.Kit;
import fr.gabidut76.westerlife.common.objects.kits.KitRules;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@StyleToLoad
public class CSSGuiCreateKit extends GuiFrame {

    public CSSGuiCreateKit() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("Créer un kit.");
        title.setCssClass("title");
        background.add(title);

        setEnableDebugPanel(true);

        GuiTextField name = new GuiTextField();
        name.setCssClass("name");
        name.setText("Name");
        background.add(name);

        GuiTextField description = new GuiTextField();
        description.setCssClass("description");
        description.setText("Description");
        background.add(description);

        GuiButton create = new GuiButton("Create");
        create.setCssClass("create");
        background.add(create);

        create.addClickListener((mouseX, mouseY, mouseButton) -> {

            List<NBTTagCompound> items = Minecraft.getMinecraft().player.inventoryContainer.getInventory().stream().map(stack -> {
                NBTTagCompound nbt = new NBTTagCompound();
                stack.writeToNBT(nbt);
                return nbt;
            }).collect(Collectors.toList());
            if (!Minecraft.getMinecraft().player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                ClientNotifications.notifications.add(new Notification("Kit erreur", "CapaNull", 0xFF0000, System.currentTimeMillis()));
            }
            IExtraItemHandler cap = Minecraft.getMinecraft().player.getCapability(ExtraItemCapability.CAPABILITY, null);
            for (int i = 0; i < Objects.requireNonNull(cap).getSlots(); i++) {
                ItemStack stack = cap.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    stack.writeToNBT(nbt);
                    items.add(nbt);
                }
            }

            items = items.stream().filter(nbt -> !nbt.getString("id").equals("minecraft:air")).collect(Collectors.toList());

            Main.network.sendToServer(new PacketCreateKit(new Kit(name.getText(), description.getText(), items, KitRules.ADD_TO_INVENTORY)));
        });

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/cardealer.css"));
    }
}