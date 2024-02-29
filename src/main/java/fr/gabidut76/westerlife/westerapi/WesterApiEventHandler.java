package fr.gabidut76.westerlife.westerapi;

import fr.gabidut76.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@SideOnly(Side.SERVER)
@Mod.EventBusSubscriber(Side.SERVER)
public class WesterApiEventHandler {






}
