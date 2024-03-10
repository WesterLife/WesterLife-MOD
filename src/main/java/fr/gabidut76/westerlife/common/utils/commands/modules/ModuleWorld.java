package fr.gabidut76.westerlife.common.utils.commands.modules;

//import fr.yan36.westerlife.common.blocks.tileentity.TEDigicode;

import com.jme3.math.Vector3f;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.gabidut76.westerlife.common.blocks.tileentity.*;
import fr.gabidut76.westerlife.common.entities.npc.NPCConcessEntity;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import fr.gabidut76.westerlife.common.objects.LightSequence;
import fr.gabidut76.westerlife.common.objects.gameplay.MacdoCommand;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleWorld extends CommandModule {

    public ModuleWorld() {
        super("world");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args.length > 1) {
            if (Objects.equals(args[1], "help")) {
                sender.sendMessage(new TextComponentString("§c/wlmod world <help/list>"));
            } else if (Objects.equals(args[1], "barrierelevante")) {
                if (Objects.equals(args[2], "adduser") || Objects.equals(args[2], "removeuser") || Objects.equals(args[2], "listusers")) {
                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if (rayTraceResult != null) {
                            TileMovingGate tileMovingGate = (TileMovingGate) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if (tileMovingGate != null) {
                                if (Objects.equals(args[2], "adduser")) {
                                    tileMovingGate.addPlayer(args[3]);
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aUser added"));
                                } else if (Objects.equals(args[2], "removeuser")) {
                                    tileMovingGate.removePlayer(args[3]);
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aUser removed"));
                                }
                                if (Objects.equals(args[2], "listusers")) {
                                    sender.sendMessage(new TextComponentString("§aUsers: " + tileMovingGate.getPlayer()));
                                }

                            }
                        }

                    }
                }
            } else if (Objects.equals(args[1], "radar")) {

                if (Objects.equals(args[2], "setspeed")) {
                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if (rayTraceResult != null) {
                            TileRadarFixe tileMovingGate = (TileRadarFixe) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if (tileMovingGate != null) {
                                if (Objects.equals(args[2], "setspeed")) {
                                    tileMovingGate.setSpeed(Integer.parseInt(args[3]));
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aSet speed to " + args[3] + " km/h"));
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(new TextComponentString("§c/wlmod world radar <setspeed>"));
                    }
                }

            } else if (Objects.equals(args[1], "itemnbt")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    player.sendMessage(new TextComponentString("§a NBT IN HAND : " + player.getHeldItemMainhand().getTagCompound()));
                }

            } else if (Objects.equals(args[1], "setconcesspnj")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;

                    Entity target = Util.getEntityLookAt(player, 10);

                    if(target instanceof NPCConcessEntity) {
                        NPCConcessEntity npcConcessEntity = (NPCConcessEntity) target;
                        npcConcessEntity.setConcessID(args[2]);
                        sender.sendMessage(new TextComponentString("§aSet Concess ID to " + args[2]));
                    } else {
                        sender.sendMessage(new TextComponentString("§cNo NPC found"));
                    }
                }

            } else if (Objects.equals(args[1], "entitynbt")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;

                    Entity target = Util.getEntityLookAt(player, 10);

                    player.sendMessage(new TextComponentString("§a TARGET NBT : " + target + " " + target.serializeNBT()));
                }

            } else if (Objects.equals(args[1], "macdorecipe")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;

                    List<TileMacdo.burger> ingredients = MacdoCommand.RECIPES.get(Integer.parseInt(args[2])).ingredients;
                    String burgerComposition = ingredients.stream().map(Enum::name).reduce((s, s2) -> s + ", " + s2).orElse("Empty");
                    player.sendMessage(new TextComponentString("Your burger is composed of: " + burgerComposition + " : " + MacdoCommand.RECIPES.get(Integer.parseInt(args[2])).name)) ;

                    ItemStack burger = new ItemStack(DynamXInit.burger);
                    burger.setTagCompound(new net.minecraft.nbt.NBTTagCompound());
                    burger.getTagCompound().setString("burger", burgerComposition);
                    player.inventory.addItemStackToInventory(burger);
                }

            } else if (Objects.equals(args[1], "bnbt")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        player.world.getTileEntity(rayTraceResult.getBlockPos()).writeToNBT(nbtTagCompound);
                        player.sendMessage(new TextComponentString("§a TARGET NBT : " + nbtTagCompound));
                    }
                }

            } else if (Objects.equals(args[1], "tombe")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileTombe tileTombe = (TileTombe) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tileTombe != null) {
                            tileTombe.setText(args[2].replaceAll("_", " "));
                            tileTombe.sync();
                            sender.sendMessage(new TextComponentString("§aSet text to '" + args[2] + "'."));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world radar <setspeed>"));
                }


            } else if (Objects.equals(args[1], "spot")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileSpot tilespot = (TileSpot) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilespot != null) {
                            tilespot.setAngle(Integer.parseInt(args[2]));
                            tilespot.sync();
                            tilespot.syncToClient();
                            sender.sendMessage(new TextComponentString("§aSet angle to '" + args[2] + "'°."));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "portenom")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TilePorteNom tilePorteNom = (TilePorteNom) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilePorteNom != null) {
                            tilePorteNom.setName(args[2].replaceAll("_", " "));
                            tilePorteNom.setFunction(args[3].replaceAll("_", " "));
                            tilePorteNom.sync();
                            tilePorteNom.syncToClient();
                            sender.sendMessage(new TextComponentString("§aSet angle to '" + args[2] + "'°."));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "registry")) {
                sender.sendMessage(new TextComponentString("§aRegistry : " + DynamXObjectLoaders.WHEELED_VEHICLES.getInfos()));
            } else if (Objects.equals(args[1], "carpresentation")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileCarPresentation tilePorteNom = (TileCarPresentation) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilePorteNom != null) {
                            System.out.println(DynamXObjectLoaders.WHEELED_VEHICLES.getInfos());
                            tilePorteNom.setCar(args[2]);

                            tilePorteNom.sync();
                            tilePorteNom.syncToClient();
                            sender.sendMessage(new TextComponentString("§aSet car to '" + args[2] + "'."));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "sequence")) {
                if (Objects.equals(args[2], "create")) {
                    LightSequence lightSequence = new LightSequence();
                    lightSequence.add(new BlockPos(0, -20, 0));
//                    Main.wl_db.setString("seq_" + args[3], new LightSequence().toString());
                    sender.sendMessage(new TextComponentString("§aSequence " + args[3] + " created"));
                } else if (Objects.equals(args[2], "addlyre")) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {}
//                        TileLyre tilespot = (TileLyre) player.world.getTileEntity(rayTraceResult.getBlockPos());
//                        if (tilespot != null) {
//                            LightSequence lightSequence = LightSequence.fromString(Main.wl_db.getString("seq_" + args[3]));
//                            lightSequence.add(tilespot.getPos());
//                            Main.wl_db.setString("seq_" + args[3], lightSequence.toString());
//                            sender.sendMessage(new TextComponentString("§aLyre added to sequence " + args[3]));
//                        }
//                    }
//                } else if (Objects.equals(args[2], "addseq")) {
//                    Vector3f vector3fTo = new Vector3f(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
//                    LightSequence lightSequence = LightSequence.fromString(Main.wl_db.getString("seq_" + args[6]));
//                    lightSequence.add(new LightSequence.DoubleVector(new Vector3f(0, 0, 0), vector3fTo));
//                    Main.wl_db.setString("seq_" + args[6], lightSequence.toString());
//                    sender.sendMessage(new TextComponentString("§aSequence added to sequence " + args[3] + " " + args[4] + " " + args[5] + " " + args[6]));
//                } else if (Objects.equals(args[2], "play")) {
//                    LightSequence lightSequence = LightSequence.fromString(Main.wl_db.getString("seq_" + args[3]));
//                    lightSequence.play(sender.getEntityWorld());
                } else if (Objects.equals(args[2], "dump")) {
//                    LightSequence lightSequence = LightSequence.fromString(Main.wl_db.getString("seq_" + args[3]));
//                    sender.sendMessage(new TextComponentString(lightSequence.toString()));
                } else if (Objects.equals(args[2], "bl")) {
                    // make the block blink
                    BlockPos blockPos = new BlockPos(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                    TileLyre tileEntity = (TileLyre) sender.getEntityWorld().getTileEntity(blockPos);

                    if (tileEntity != null) {
                        tileEntity.setBlink(!tileEntity.isBlink());
                        tileEntity.sync();
                        tileEntity.syncToClient();
                    }
                } else if (Objects.equals(args[2], "gui")) {
//                    Main.network.sendTo(new PacketOpenAcsGui(1, Main.wl_db.getString("seq_" + args[3]), args[3]), (EntityPlayerMP) sender);
                } else if (Objects.equals(args[2], "manuset")) {
//                    Main.wl_db.setString("seq_" + args[3], args[4]);
                } else if (Objects.equals(args[2], "reset")) {
//                    LightSequence lightSequence = LightSequence.fromString(Main.wl_db.getString("seq_" + args[3]));
//                    lightSequence.reset(sender.getEntityWorld());
                }


            } else if (Objects.equals(args[1], "blockcolor")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileColoredBlock tilechair = (TileColoredBlock) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilechair != null) {

                            if (args.length == 3) {
                                tilechair.setColor(Integer.parseInt(args[2]));
                                tilechair.sync();
                                tilechair.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet color to '" + args[2] + "'."));
                            } else {
                                // rbg color of arg 2, 3, 4
                                int r = Integer.parseInt(args[2]);
                                int g = Integer.parseInt(args[3]);
                                int b = Integer.parseInt(args[4]);

                                // convert to 0xRRGGBB
                                int color = (r << 16) + (g << 8) + b;
                                tilechair.setColor(color);
                                tilechair.sync();
                                tilechair.syncToClient();

                                sender.sendMessage(new TextComponentString("§aSet color to '" + args[2] + " " + args[3] + " " + args[4] + "'."));
                            }
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "chair")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileChair tilechair = (TileChair) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilechair != null) {

                            if (args.length == 3) {
                                tilechair.setColor(Integer.parseInt(args[2]));
                                tilechair.sync();
                                tilechair.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet color to '" + args[2] + "'."));
                            } else {
                                // rbg color of arg 2, 3, 4
                                int r = Integer.parseInt(args[2]);
                                int g = Integer.parseInt(args[3]);
                                int b = Integer.parseInt(args[4]);

                                // convert to 0xRRGGBB
                                int color = (r << 16) + (g << 8) + b;
                                tilechair.setColor(color);
                                tilechair.sync();
                                tilechair.syncToClient();

                                sender.sendMessage(new TextComponentString("§aSet color to '" + args[2] + " " + args[3] + " " + args[4] + "'."));
                            }
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "lyre")) {

                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    System.out.println(rayTraceResult.getBlockPos());
                    if (rayTraceResult != null) {
                        TileLyre tilespot = (TileLyre) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilespot != null) {
                            if (Objects.equals(args[2], "setfrom")) {
                                tilespot.setRotationfrom(new Vector2f(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
                                tilespot.sync();
                                tilespot.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet from to '" + args[3] + " " + args[4] + "'°."));
                            }
                            if (Objects.equals(args[2], "setto")) {
                                tilespot.setRotationto(new Vector2f(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
                                tilespot.sync();
                                tilespot.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet to '" + args[3] + " " + args[4] + "'°."));
                            }
                            if (Objects.equals(args[2], "act")) {
                                tilespot.setActualrotation(new Vector2f(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
                                tilespot.sync();
                                tilespot.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet act '" + args[3] + " " + args[4] + "'°."));
                            }
                            if (Objects.equals(args[2], "setmax")) {
                                tilespot.setTimeMax(Integer.parseInt(args[3]));
                                tilespot.sync();
                                tilespot.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet max '" + args[3] + "' ticks."));
                            }
                            if (Objects.equals(args[2], "flip")) {
                                tilespot.setFlip(Boolean.parseBoolean(args[3]));
                                tilespot.sync();
                                tilespot.syncToClient();
                                sender.sendMessage(new TextComponentString("§aSet flip : '" + args[3] + "' ."));
                            }
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("§c/wlmod world spot <angle>"));
                }


            } else if (Objects.equals(args[1], "feurouge")) {

                if (Objects.equals(args[2], "setpos") || Objects.equals(args[2], "setsync")) {
                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if (rayTraceResult != null) {
                            TileFeuRouge tilefeurouge = (TileFeuRouge) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if (tilefeurouge != null) {
                                if (Objects.equals(args[2], "setpos")) {
                                    tilefeurouge.setPosition(Integer.parseInt(args[3]));
                                    tilefeurouge.sync();
                                    sender.sendMessage(new TextComponentString("§aSet position to '" + args[3] + "'."));
                                } else if (Objects.equals(args[2], "setsync")) {
                                    tilefeurouge.setSyncvalue(Integer.parseInt(args[3]));
                                    tilefeurouge.sync();
                                    sender.sendMessage(new TextComponentString("§aSet sync to '" + args[3] + "'."));
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(new TextComponentString("§c/wlmod world feurouge <setpos/setsync>"));
                    }
                }


            } else if (Objects.equals(args[1], "resetwater")) {
//                Databases.getPlayerData((EntityPlayer) sender).setFloat("watervalue", 100f);
            } else if (Objects.equals(args[1], "setbank")) {
                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    if (!player.getHeldItemMainhand().hasTagCompound()) {
                        player.getHeldItemMainhand().setTagCompound(new NBTTagCompound());
                        System.out.println("§c No NBT found, creating one");
                    }
                    player.getHeldItemMainhand().getTagCompound().setString("relatedBankAccount", args[2]);
                    sender.sendMessage(new TextComponentString("§aBank set to " + args[2]));
                }
            } else if (Objects.equals(args[1], "notif")) {
//                Databases.getPlayerData((EntityPlayer) sender).setString("notification", "Bonjour, ceci est un test de §lnotification !");
                sender.sendMessage(new TextComponentString("§aNotification set !"));
            } else if (Objects.equals(args[1], "digicode")) {
                //Change the code of a digicode
                if (Objects.equals(args[2], "setcode") || Objects.equals(args[2], "getcode")) {
                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
//                        if(rayTraceResult != null) {
//                            TEDigicode teDigicode = (TEDigicode) player.world.getTileEntity(rayTraceResult.getBlockPos());
//                            if(teDigicode != null) {
//                                if(Objects.equals(args[2], "setcode")) {
//                                    teDigicode.setCode(args[3]);
//                                    teDigicode.sync();
//                                    sender.sendMessage(new TextComponentString("§aCode changed"));
//                                } else if(Objects.equals(args[2], "getcode")) {
//                                    sender.sendMessage(new TextComponentString("§aCode: " + teDigicode.getCode()));
//                                }
//                            }
//                        }

                    }
                }
            } else if (Objects.equals(args[1], "pagglo")) {
                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    if (rayTraceResult != null) {
                        TilePanneauAgglomeration tilePanneauAgglomeration = (TilePanneauAgglomeration) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilePanneauAgglomeration != null) {
                            tilePanneauAgglomeration.setName(args[2].replaceAll("_", " "));
                            tilePanneauAgglomeration.sync();
                            tilePanneauAgglomeration.syncToClient();
                        }
                    }
                }
            } else if (Objects.equals(args[1], "prue")) {
                if (sender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) sender;
                    RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                    assert rayTraceResult != null;
                    if (rayTraceResult != null) {
                        TilePanneauRue tilePanneauRue = (TilePanneauRue) player.world.getTileEntity(rayTraceResult.getBlockPos());
                        if (tilePanneauRue != null) {
                            if (args[2].equals("type")) {
                                tilePanneauRue.setType(BlockPanneauRue.Type.valueOf(args[3]));
                                tilePanneauRue.sync();
                                tilePanneauRue.syncToClient();
                            } else {
                                tilePanneauRue.setName(args[2].replaceAll("_", " "));
                                tilePanneauRue.sync();
                                tilePanneauRue.syncToClient();
                            }
                        }
                    }
                }
            }
        } else {
            sender.sendMessage(new TextComponentString("§c/wlmod world <help/barrierelevante>"));
        }
    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[]
            args, @Nullable BlockPos targetPos) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2) {
            list.add("help");
            list.add("barrierelevante");
            list.add("radar");
            list.add("tombe");
            list.add("spot");
            list.add("portenom");
            list.add("sequence");
            list.add("blockcolor");
            list.add("chair");
            list.add("lyre");
            list.add("feurouge");
            list.add("resetwater");
            list.add("notif");
            list.add("digicode");
            list.add("pagglo");
            list.add("prue");
        } else if (args.length == 3) {
            switch (args[1]) {
                case "barrierelevante":
                    list.add("adduser");
                    list.add("removeuser");
                    list.add("listusers");
                    break;
                case "radar":
                    list.add("setspeed");
                    break;
                case "sequence":
                    list.add("create");
                    list.add("addlyre");
                    list.add("addseq");
                    list.add("play");
                    list.add("dump");
                    list.add("bl");
                    list.add("gui");
                    list.add("manuset");
                    list.add("reset");
                    break;
                case "blockcolor":
                case "chair":
                case "feurouge":
                    list.add("setpos");
                    list.add("setsync");
                    break;
                case "lyre":
                    list.add("setfrom");
                    list.add("setto");
                    list.add("act");
                    list.add("setmax");
                    list.add("flip");
                    break;
            }
        } else if (args.length == 4 && args[1].equals("sequence")) {
            if (args[2].equals("addlyre")) {
                list.add("<sequence_name>");
            } else if (args[2].equals("addseq")) {
                list.add("<x>");
            }
        } else if (args.length == 5 && args[1].equals("sequence")) {
            if (args[2].equals("addseq")) {
                list.add("<y>");
            }
        } else if (args.length == 6 && args[1].equals("sequence")) {
            if (args[2].equals("addseq")) {
                list.add("<z>");
            }
        } else if (args.length == 7 && args[1].equals("sequence")) {
            if (args[2].equals("addseq")) {
                list.add("<sequence_name>");
            }
        }
        return CommandBase.getListOfStringsMatchingLastWord(args, list);
    }
}
