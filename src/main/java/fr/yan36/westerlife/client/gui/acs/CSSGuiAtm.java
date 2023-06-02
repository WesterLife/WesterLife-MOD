package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiPasswordField;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.network.old.PacketATMTransaction;
import fr.yan36.westerlife.common.network.old.PacketChangerCodeServer;
import fr.yan36.westerlife.common.network.old.PacketDepoArgentServer;
import fr.yan36.westerlife.common.network.old.PacketRetirerArgentServer;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class CSSGuiAtm {
}