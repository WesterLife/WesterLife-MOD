package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.entreprises.Company;
import fr.yan36.westerlife.common.objects.entreprises.CompanyType;
import fr.yan36.westerlife.common.objects.entreprises.types.Employee;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.entreprises.CompanyManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class ModuleCompany extends CommandModule {

    public ModuleCompany() {
        super("entreprise");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        CompanyManager manager = CompanyManager.getInstance();

        if (args.length < 2 || args[1].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return;
        }

        String action = args[1].toLowerCase();

        switch (action) {
            case "list": {
                Collection<Company> all = manager.getAllCompanies();
                sender.sendMessage(new TextComponentString("§b========== Liste des entreprises (Admin) =========="));
                for (Company c : all) {
                    sender.sendMessage(new TextComponentString("§e#" + c.getId() + " §a" + c.getName() + " §7(SIRET: §e" + c.getSiret() + "§7, Compte: §e" + c.getAccountNumber() + "§7) Solde: §6" + manager.getCompanyBalance(c) + "€"));
                }
                sender.sendMessage(new TextComponentString("§b==================================================="));
                break;
            }

            case "info": {
                if (args.length < 3) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise info <nom|siret>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                sender.sendMessage(new TextComponentString("§b========== Entreprise : " + c.getName() + " =========="));
                sender.sendMessage(new TextComponentString("§9ID §7: §e" + c.getId() + " §7| §9SIRET §7: §e" + c.getSiret()));
                sender.sendMessage(new TextComponentString("§9Type §7: §b" + c.getCompanyType().getFullName()));
                sender.sendMessage(new TextComponentString("§9Propriétaire UUID §7: §e" + c.getOwnerUuid()));
                sender.sendMessage(new TextComponentString("§9Compte §7: §e" + c.getAccountNumber() + " §7| §9Solde §7: §a" + manager.getCompanyBalance(c) + "€"));
                sender.sendMessage(new TextComponentString("§9Employés §7: §e" + c.getEmployees().size()));
                sender.sendMessage(new TextComponentString("§b==============================================="));
                break;
            }

            case "create": {
                if (args.length < 5) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise create <nom> <type> <joueur_patron>"));
                    return;
                }
                String name = args[2];
                CompanyType type = CompanyType.fromString(args[3]);
                String targetPlayer = args[4];

                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(targetPlayer);
                UUID ownerUuid;
                if (target != null) {
                    Character ch = DBUtils.getActiveCharacter(target);
                    ownerUuid = (ch != null) ? ch.getUuid() : target.getUniqueID();
                } else {
                    try {
                        ownerUuid = UUID.fromString(targetPlayer);
                    } catch (Exception ex) {
                        sender.sendMessage(new TextComponentString("§cJoueur introuvable : §e" + targetPlayer));
                        return;
                    }
                }

                Company created = manager.createCompany(name, type, ownerUuid, type.getMinCapital());
                if (created != null) {
                    sender.sendMessage(new TextComponentString("§aEntreprise §b" + name + " §acréée avec succès ! ID: §e" + created.getId() + " §aSIRET: §e" + created.getSiret()));
                } else {
                    sender.sendMessage(new TextComponentString("§cErreur lors de la création de l'entreprise."));
                }
                break;
            }

            case "delete": {
                if (args.length < 3) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise delete <nom|siret>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                String cName = c.getName();
                if (manager.deleteCompany(c)) {
                    sender.sendMessage(new TextComponentString("§aEntreprise §b" + cName + " §asupprimée avec succès."));
                } else {
                    sender.sendMessage(new TextComponentString("§cErreur lors de la suppression."));
                }
                break;
            }

            case "addmoney": {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise addmoney <nom|siret> <montant>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                try {
                    double toAdd = Double.parseDouble(args[3]);
                    double current = manager.getCompanyBalance(c);
                    manager.setCompanyBalance(c, current + toAdd);
                    sender.sendMessage(new TextComponentString("§aAjouté §e" + toAdd + "€ §aau compte de §b" + c.getName() + " §a(Nouveau solde : §e" + (current + toAdd) + "€§a)."));
                } catch (NumberFormatException e) {
                    sender.sendMessage(new TextComponentString("§cMontant invalide."));
                }
                break;
            }

            case "removemoney": {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise removemoney <nom|siret> <montant>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                try {
                    double toRemove = Double.parseDouble(args[3]);
                    double current = manager.getCompanyBalance(c);
                    double next = Math.max(0, current - toRemove);
                    manager.setCompanyBalance(c, next);
                    sender.sendMessage(new TextComponentString("§aRetiré §e" + toRemove + "€ §adu compte de §b" + c.getName() + " §a(Nouveau solde : §e" + next + "€§a)."));
                } catch (NumberFormatException e) {
                    sender.sendMessage(new TextComponentString("§cMontant invalide."));
                }
                break;
            }

            case "setowner": {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise setowner <nom|siret> <joueur>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[3]);
                UUID ownerUuid;
                if (target != null) {
                    Character ch = DBUtils.getActiveCharacter(target);
                    ownerUuid = (ch != null) ? ch.getUuid() : target.getUniqueID();
                } else {
                    try {
                        ownerUuid = UUID.fromString(args[3]);
                    } catch (Exception ex) {
                        sender.sendMessage(new TextComponentString("§cJoueur introuvable : §e" + args[3]));
                        return;
                    }
                }
                if (manager.setCompanyOwner(c, ownerUuid)) {
                    sender.sendMessage(new TextComponentString("§aLe dirigeant de §b" + c.getName() + " §aa été changé."));
                } else {
                    sender.sendMessage(new TextComponentString("§cErreur lors du changement de dirigeant."));
                }
                break;
            }

            case "hire": {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise hire <nom|siret> <joueur> [rang]"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[3]);
                UUID targetUuid;
                if (target != null) {
                    Character ch = DBUtils.getActiveCharacter(target);
                    targetUuid = (ch != null) ? ch.getUuid() : target.getUniqueID();
                } else {
                    try {
                        targetUuid = UUID.fromString(args[3]);
                    } catch (Exception ex) {
                        sender.sendMessage(new TextComponentString("§cJoueur introuvable : §e" + args[3]));
                        return;
                    }
                }
                String rank = (args.length >= 5) ? args[4] : "Employé";
                if (manager.hireEmployee(c, targetUuid, rank)) {
                    sender.sendMessage(new TextComponentString("§aEmployé recruté avec succès dans §b" + c.getName()));
                } else {
                    sender.sendMessage(new TextComponentString("§cErreur lors du recrutement (déjà employé ou erreur BDD)."));
                }
                break;
            }

            case "fire": {
                if (args.length < 4) {
                    sender.sendMessage(new TextComponentString("§cUsage : §e/wlmod entreprise fire <nom|siret> <joueur>"));
                    return;
                }
                Company c = manager.findCompany(args[2]);
                if (c == null) {
                    sender.sendMessage(new TextComponentString("§cEntreprise introuvable : §e" + args[2]));
                    return;
                }
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[3]);
                UUID targetUuid = null;
                if (target != null) {
                    Character ch = DBUtils.getActiveCharacter(target);
                    targetUuid = (ch != null) ? ch.getUuid() : target.getUniqueID();
                } else {
                    for (Employee emp : c.getEmployees()) {
                        if (emp.getDisplayName(server).equalsIgnoreCase(args[3]) || emp.getCharacterUuid().toString().startsWith(args[3])) {
                            targetUuid = emp.getCharacterUuid();
                            break;
                        }
                    }
                }
                if (targetUuid == null) {
                    sender.sendMessage(new TextComponentString("§cEmployé introuvable dans cette entreprise."));
                    return;
                }
                if (manager.fireEmployee(c, targetUuid)) {
                    sender.sendMessage(new TextComponentString("§aEmployé retiré de §b" + c.getName()));
                } else {
                    sender.sendMessage(new TextComponentString("§cErreur lors du licenciement."));
                }
                break;
            }

            case "reload": {
                manager.loadAll();
                sender.sendMessage(new TextComponentString("§aToutes les entreprises ont été rechargées depuis la base de données (" + manager.getAllCompanies().size() + " chargées)."));
                break;
            }

            default:
                sendHelp(sender);
                break;
        }
    }

    private void sendHelp(ICommandSender sender) {
        sender.sendMessage(new TextComponentString("§b========== Administration des Entreprises (/wlmod entreprise) =========="));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise list §7- Lister toutes les entreprises"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise info <nom|siret> §7- Informations"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise create <nom> <type> <joueur> §7- Créer une entreprise"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise delete <nom|siret> §7- Supprimer une entreprise"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise addmoney <nom|siret> <montant> §7- Ajouter des fonds"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise removemoney <nom|siret> <montant> §7- Retirer des fonds"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise setowner <nom|siret> <joueur> §7- Définir le dirigeant"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise hire <nom|siret> <joueur> [rang] §7- Recruter un joueur"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise fire <nom|siret> <joueur> §7- Licencier un joueur"));
        sender.sendMessage(new TextComponentString("§9/wlmod entreprise reload §7- Recharger les entreprises depuis la BDD"));
        sender.sendMessage(new TextComponentString("§b========================================================================="));
    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 2) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "help", "list", "info", "create", "delete", "addmoney", "removemoney", "setowner", "hire", "fire", "reload");
        }

        CompanyManager manager = CompanyManager.getInstance();

        if (args.length == 3) {
            String act = args[1].toLowerCase();
            if (act.equals("info") || act.equals("delete") || act.equals("addmoney") || act.equals("removemoney") || act.equals("setowner") || act.equals("hire") || act.equals("fire")) {
                List<String> list = manager.getAllCompanies().stream().map(Company::getName).collect(Collectors.toList());
                return CommandBase.getListOfStringsMatchingLastWord(args, list);
            }
        }

        if (args.length == 4) {
            String act = args[1].toLowerCase();
            if (act.equals("create")) {
                List<String> types = Arrays.stream(CompanyType.values()).map(CompanyType::name).collect(Collectors.toList());
                return CommandBase.getListOfStringsMatchingLastWord(args, types);
            }
            if (act.equals("setowner") || act.equals("hire") || act.equals("fire")) {
                return CommandBase.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            }
        }

        if (args.length == 5) {
            String act = args[1].toLowerCase();
            if (act.equals("create")) {
                return CommandBase.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            }
        }

        return Collections.emptyList();
    }
}
