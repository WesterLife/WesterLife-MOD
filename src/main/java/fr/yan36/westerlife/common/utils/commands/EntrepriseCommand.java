package fr.yan36.westerlife.common.utils.commands;

import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.entreprises.Company;
import fr.yan36.westerlife.common.objects.entreprises.CompanyType;
import fr.yan36.westerlife.common.objects.entreprises.types.Employee;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.entreprises.CompanyManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class EntrepriseCommand extends CommandBase {

    @Override
    public String getName() {
        return "entreprise";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("company", "societe", "corp", "entr");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/entreprise help";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("§cCette commande doit être exécutée par un joueur."));
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) sender;
        Character character = DBUtils.getActiveCharacter(player);
        UUID charUuid = (character != null) ? character.getUuid() : player.getUniqueID();
        CompanyManager manager = CompanyManager.getInstance();

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(player);
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "list": {
                Collection<Company> all = manager.getAllCompanies();
                player.sendMessage(new TextComponentString("§6========== Entreprises du serveur (" + all.size() + ") =========="));
                if (all.isEmpty()) {
                    player.sendMessage(new TextComponentString("§7Aucune entreprise enregistrée pour le moment."));
                } else {
                    for (Company c : all) {
                        String ownerName = resolvePlayerName(server, c.getOwnerUuid());
                        player.sendMessage(new TextComponentString("§e#" + c.getId() + " §b" + c.getName() + " §7(" + c.getCompanyType().getShortName() + ") §7- Dirigeant: §f" + ownerName + " §7(§a" + c.getEmployees().size() + " emp.§7)"));
                    }
                }
                player.sendMessage(new TextComponentString("§6=================================================="));
                break;
            }

            case "info": {
                Company targetCompany = null;
                if (args.length >= 2) {
                    targetCompany = manager.findCompany(args[1]);
                    if (targetCompany == null) {
                        player.sendMessage(new TextComponentString("§cAucune entreprise trouvée avec l'identifiant : §e" + args[1]));
                        return;
                    }
                } else {
                    targetCompany = manager.getCompanyByEmployee(charUuid);
                    if (targetCompany == null) {
                        player.sendMessage(new TextComponentString("§cVous ne faites partie d'aucune entreprise. Utilisez §e/entreprise info <nom|siret>"));
                        return;
                    }
                }

                String ownerName = resolvePlayerName(server, targetCompany.getOwnerUuid());
                double solde = manager.getCompanyBalance(targetCompany);

                player.sendMessage(new TextComponentString("§6========== Entreprise : §b" + targetCompany.getName() + " §6=========="));
                player.sendMessage(new TextComponentString("§9Numéro SIRET §7: §e" + targetCompany.getSiret()));
                player.sendMessage(new TextComponentString("§9Forme juridique §7: §b" + targetCompany.getCompanyType().getFullName() + " (" + targetCompany.getCompanyType().getShortName() + ")"));
                player.sendMessage(new TextComponentString("§9Dirigeant §7: §e" + ownerName));
                player.sendMessage(new TextComponentString("§9Compte bancaire §7: §e" + targetCompany.getAccountNumber() + " §7| §9Solde §7: §a" + solde + "€"));
                player.sendMessage(new TextComponentString("§9Date de création §7: §7" + targetCompany.getCreationDate()));
                player.sendMessage(new TextComponentString("§9Nombre d'employés §7: §e" + targetCompany.getEmployees().size()));

                StringBuilder ranksStr = new StringBuilder();
                for (Rank r : targetCompany.getRanks()) {
                    if (ranksStr.length() > 0) ranksStr.append(", ");
                    ranksStr.append(r.getName()).append(" (Lvl ").append(r.getLevel()).append(")");
                }
                player.sendMessage(new TextComponentString("§9Rangs disponibles §7: §7[" + ranksStr + "]"));
                player.sendMessage(new TextComponentString("§6=================================================="));
                break;
            }

            case "my": {
                Company myComp = manager.getCompanyByEmployee(charUuid);
                if (myComp == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes actuellement employé dans aucune entreprise."));
                    return;
                }
                Rank rank = myComp.getRankOf(charUuid);
                String rankName = (rank != null) ? rank.getName() : "Inconnu";
                float salary = (rank != null) ? rank.getSalary() : 0f;
                double compSolde = manager.getCompanyBalance(myComp);

                player.sendMessage(new TextComponentString("§6========== Votre Entreprise =========="));
                player.sendMessage(new TextComponentString("§9Entreprise §7: §b" + myComp.getName() + " §7(" + myComp.getCompanyType().getShortName() + ")"));
                player.sendMessage(new TextComponentString("§9Votre rang §7: §e" + rankName + (myComp.isOwner(charUuid) ? " §a[PROPRIÉTAIRE]" : "")));
                player.sendMessage(new TextComponentString("§9Votre salaire §7: §e" + salary + "€"));
                player.sendMessage(new TextComponentString("§9Trésorerie §7: §a" + compSolde + "€"));
                player.sendMessage(new TextComponentString("§9Effectif total §7: §e" + myComp.getEmployees().size() + " employé(s)"));
                player.sendMessage(new TextComponentString("§6======================================"));
                break;
            }

            case "create": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise create <Nom> [SARL|SAS|SA|ASSOCIATION|PUBLIQUE|MICRO]"));
                    return;
                }

                String compName = args[1];
                CompanyType type = CompanyType.SARL;
                if (args.length >= 3) {
                    type = CompanyType.fromString(args[2]);
                }

                if (manager.getCompanyByName(compName) != null) {
                    player.sendMessage(new TextComponentString("§cUne entreprise avec ce nom existe déjà !"));
                    return;
                }

                // Check minimum capital requirement
                float minCap = type.getMinCapital();
                String personalAcc = DBUtils.getPersonalBankAccount(charUuid, player.getUniqueID());
                double personalSolde = (personalAcc != null) ? DBUtils.getAccountBalance(personalAcc) : 0.0;

                if (minCap > 0 && personalSolde < minCap) {
                    player.sendMessage(new TextComponentString("§cCapital insuffisant ! La création d'une " + type.getShortName() + " requiert un capital minimum de §e" + minCap + "€§c. (Votre solde : " + personalSolde + "€)"));
                    return;
                }

                // Deduct capital from personal account if applicable
                if (minCap > 0 && personalAcc != null) {
                    DBUtils.setAccountBalance(personalAcc, personalSolde - minCap);
                }

                Company created = manager.createCompany(compName, type, charUuid, minCap);
                if (created == null) {
                    player.sendMessage(new TextComponentString("§cErreur lors de la création de l'entreprise dans la base de données."));
                    return;
                }

                player.sendMessage(new TextComponentString("§aFélicitations ! Votre entreprise §b" + created.getName() + " §a(" + type.getShortName() + ") a été créée avec succès !"));
                player.sendMessage(new TextComponentString("§aNuméro SIRET : §e" + created.getSiret() + " §a| Compte bancaire n° §e" + created.getAccountNumber()));
                break;
            }

            case "delete": {
                Company toDelete = null;
                if (args.length >= 2) {
                    toDelete = manager.findCompany(args[1]);
                } else {
                    toDelete = manager.getCompanyByEmployee(charUuid);
                }

                if (toDelete == null) {
                    player.sendMessage(new TextComponentString("§cEntreprise introuvable."));
                    return;
                }

                boolean isOwner = toDelete.isOwner(charUuid);
                boolean isOp = player.canUseCommand(2, "westerlife.command.wlmod");
                if (!isOwner && !isOp) {
                    player.sendMessage(new TextComponentString("§cSeul le propriétaire de l'entreprise peut la supprimer."));
                    return;
                }

                String delName = toDelete.getName();
                if (manager.deleteCompany(toDelete)) {
                    player.sendMessage(new TextComponentString("§aL'entreprise §b" + delName + " §aet ses comptes associés ont été dissous avec succès."));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors de la suppression de l'entreprise."));
                }
                break;
            }

            case "hire": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise hire <joueur> [rang]"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                if (!company.canManage(charUuid)) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas la permission de recruter dans cette entreprise."));
                    return;
                }

                String targetUsername = args[1];
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(targetUsername);
                if (target == null) {
                    player.sendMessage(new TextComponentString("§cLe joueur §e" + targetUsername + " §cn'est pas connecté."));
                    return;
                }

                Character targetChar = DBUtils.getActiveCharacter(target);
                UUID targetCharUuid = (targetChar != null) ? targetChar.getUuid() : target.getUniqueID();
                String targetDisplayName = (targetChar != null) ? targetChar.getFullName() : target.getName();

                if (company.hasEmployee(targetCharUuid)) {
                    player.sendMessage(new TextComponentString("§cCe joueur est déjà employé dans l'entreprise !"));
                    return;
                }

                String rankName = (args.length >= 3) ? args[2] : "Employé";
                if (company.getRank(rankName) == null) {
                    Rank lowest = company.getLowestRank();
                    rankName = (lowest != null) ? lowest.getName() : "Employé";
                }

                if (manager.hireEmployee(company, targetCharUuid, rankName)) {
                    player.sendMessage(new TextComponentString("§aVous avez embauché §e" + targetDisplayName + " §aau rang de §b" + rankName + " §adans §b" + company.getName() + "§a."));
                    target.sendMessage(new TextComponentString("§6[WesterLife] §aVous avez été embauché dans l'entreprise §b" + company.getName() + " §aen tant que §e" + rankName + " §a!"));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors du recrutement."));
                }
                break;
            }

            case "fire": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise fire <joueur>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                if (!company.canManage(charUuid)) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas la permission de licencier dans cette entreprise."));
                    return;
                }

                String targetQuery = args[1];
                UUID targetUuid = findEmployeeUuid(server, company, targetQuery);
                if (targetUuid == null) {
                    player.sendMessage(new TextComponentString("§cAucun employé correspondant à §e" + targetQuery + " §cdans votre entreprise."));
                    return;
                }

                if (company.isOwner(targetUuid)) {
                    player.sendMessage(new TextComponentString("§cImpossible de licencier le propriétaire de l'entreprise !"));
                    return;
                }

                Rank myRank = company.getRankOf(charUuid);
                Rank targetRank = company.getRankOf(targetUuid);
                if (!company.isOwner(charUuid) && myRank != null && targetRank != null && targetRank.getLevel() >= myRank.getLevel()) {
                    player.sendMessage(new TextComponentString("§cVous ne pouvez pas licencier quelqu'un de niveau égal ou supérieur au vôtre."));
                    return;
                }

                String targetName = resolvePlayerName(server, targetUuid);
                if (manager.fireEmployee(company, targetUuid)) {
                    player.sendMessage(new TextComponentString("§aVous avez licencié §e" + targetName + " §ade l'entreprise §b" + company.getName() + "§a."));
                    EntityPlayerMP target = server.getPlayerList().getPlayerByUUID(targetUuid);
                    if (target != null) {
                        target.sendMessage(new TextComponentString("§6[WesterLife] §cVous avez été licencié de l'entreprise §e" + company.getName() + "§c."));
                    }
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors du licenciement."));
                }
                break;
            }

            case "setrank": {
                if (args.length < 3) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise setrank <joueur> <nom_du_rang>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                if (!company.canManage(charUuid)) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas la permission de modifier les rangs."));
                    return;
                }

                String targetQuery = args[1];
                String rankQuery = args[2];

                UUID targetUuid = findEmployeeUuid(server, company, targetQuery);
                if (targetUuid == null) {
                    player.sendMessage(new TextComponentString("§cAucun employé correspondant à §e" + targetQuery + " §cdans votre entreprise."));
                    return;
                }

                Rank newRank = company.getRank(rankQuery);
                if (newRank == null) {
                    player.sendMessage(new TextComponentString("§cLe rang §e" + rankQuery + " §cn'existe pas dans cette entreprise."));
                    return;
                }

                Rank myRank = company.getRankOf(charUuid);
                if (!company.isOwner(charUuid) && myRank != null && newRank.getLevel() >= myRank.getLevel()) {
                    player.sendMessage(new TextComponentString("§cVous ne pouvez pas assigner un rang égal ou supérieur au vôtre."));
                    return;
                }

                if (manager.setEmployeeRank(company, targetUuid, newRank.getName())) {
                    String targetName = resolvePlayerName(server, targetUuid);
                    player.sendMessage(new TextComponentString("§aLe rang de §e" + targetName + " §aa été changé en §b" + newRank.getName() + "§a."));
                    EntityPlayerMP target = server.getPlayerList().getPlayerByUUID(targetUuid);
                    if (target != null) {
                        target.sendMessage(new TextComponentString("§6[WesterLife] §aVotre rang dans §b" + company.getName() + " §aest désormais : §e" + newRank.getName() + "§a!"));
                    }
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors de la modification du rang."));
                }
                break;
            }

            case "leave": {
                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                if (company.isOwner(charUuid)) {
                    player.sendMessage(new TextComponentString("§cVous êtes le propriétaire de l'entreprise ! Transférez la propriété avec §e/entreprise setowner <joueur> §cou dissolvez-la avec §e/entreprise delete§c."));
                    return;
                }

                String compName = company.getName();
                if (manager.fireEmployee(company, charUuid)) {
                    player.sendMessage(new TextComponentString("§aVous avez quitté l'entreprise §b" + compName + "§a."));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors de la démission."));
                }
                break;
            }

            case "setowner": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise setowner <joueur>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null || !company.isOwner(charUuid)) {
                    player.sendMessage(new TextComponentString("§cSeul le propriétaire de l'entreprise peut transférer la direction."));
                    return;
                }

                String targetQuery = args[1];
                UUID targetUuid = findEmployeeUuid(server, company, targetQuery);
                if (targetUuid == null) {
                    EntityPlayerMP t = server.getPlayerList().getPlayerByUsername(targetQuery);
                    if (t != null) {
                        Character c = DBUtils.getActiveCharacter(t);
                        targetUuid = (c != null) ? c.getUuid() : t.getUniqueID();
                    }
                }

                if (targetUuid == null) {
                    player.sendMessage(new TextComponentString("§cJoueur introuvable : §e" + targetQuery));
                    return;
                }

                if (manager.setCompanyOwner(company, targetUuid)) {
                    String newOwnerName = resolvePlayerName(server, targetUuid);
                    player.sendMessage(new TextComponentString("§aVous avez transféré la propriété de l'entreprise §b" + company.getName() + " §aà §e" + newOwnerName + "§a!"));
                    EntityPlayerMP target = server.getPlayerList().getPlayerByUUID(targetUuid);
                    if (target != null) {
                        target.sendMessage(new TextComponentString("§6[WesterLife] §aVous êtes maintenant le dirigeant de l'entreprise §b" + company.getName() + "§a!"));
                    }
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors du transfert de propriété."));
                }
                break;
            }

            case "members": {
                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                player.sendMessage(new TextComponentString("§6========== Membres de : §b" + company.getName() + " §7(" + company.getEmployees().size() + ") =========="));
                for (Employee emp : company.getEmployees()) {
                    String name = emp.getDisplayName(server);
                    Rank rank = company.getRank(emp.getRankName());
                    float salary = (rank != null) ? rank.getSalary() : 0f;
                    boolean isOwner = company.isOwner(emp.getCharacterUuid());
                    String ownerBadge = isOwner ? " §6[DIRIGEANT]" : "";

                    boolean isOnline = server.getPlayerList().getPlayerByUUID(emp.getCharacterUuid()) != null;
                    String statusBadge = isOnline ? " §a●" : " §7○";

                    player.sendMessage(new TextComponentString("§e- §f" + name + ownerBadge + " §7- Rang: §b" + emp.getRankName() + " §7(" + salary + "€)" + statusBadge));
                }
                player.sendMessage(new TextComponentString("§6=================================================="));
                break;
            }

            case "ranks": {
                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                player.sendMessage(new TextComponentString("§6========== Rangs de : §b" + company.getName() + " =========="));
                for (Rank r : company.getRanks()) {
                    player.sendMessage(new TextComponentString("§e- §b" + r.getName() + " §7(Niveau §e" + r.getLevel() + "§7, Salaire: §a" + r.getSalary() + "€§7) [Recruter: " + (r.isCanHire() ? "§aOUI" : "§cNON") + "§7, Licencier: " + (r.isCanFire() ? "§aOUI" : "§cNON") + "§7, Retirer: " + (r.isCanWithdraw() ? "§aOUI" : "§cNON") + "§7]"));
                }
                player.sendMessage(new TextComponentString("§6=================================================="));
                break;
            }

            case "addrank": {
                if (args.length < 4) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise addrank <Nom> <Niveau (1-99)> <Salaire>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null || !company.isOwner(charUuid)) {
                    player.sendMessage(new TextComponentString("§cSeul le propriétaire de l'entreprise peut ajouter des rangs."));
                    return;
                }

                String rankName = args[1];
                int level;
                float salary;
                try {
                    level = Integer.parseInt(args[2]);
                    salary = Float.parseFloat(args[3]);
                } catch (NumberFormatException e) {
                    player.sendMessage(new TextComponentString("§cLe niveau et le salaire doivent être des nombres valides."));
                    return;
                }

                if (manager.addRank(company, rankName, level, salary, level >= 50, level >= 50, level >= 80)) {
                    player.sendMessage(new TextComponentString("§aLe rang §b" + rankName + " §aa été créé avec succès !"));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors de la création du rang."));
                }
                break;
            }

            case "delrank": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise delrank <NomDuRang>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null || !company.isOwner(charUuid)) {
                    player.sendMessage(new TextComponentString("§cSeul le propriétaire de l'entreprise peut supprimer des rangs."));
                    return;
                }

                String rankName = args[1];
                if (rankName.equalsIgnoreCase("Patron")) {
                    player.sendMessage(new TextComponentString("§cImpossible de supprimer le rang Patron."));
                    return;
                }

                if (manager.removeRank(company, rankName)) {
                    player.sendMessage(new TextComponentString("§aLe rang §b" + rankName + " §aa été supprimé."));
                } else {
                    player.sendMessage(new TextComponentString("§cRang introuvable ou erreur lors de la suppression."));
                }
                break;
            }

            case "deposit": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise deposit <montant>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(args[1]);
                    if (amount <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(new TextComponentString("§cLe montant doit être un nombre positif !"));
                    return;
                }

                String personalAcc = DBUtils.getPersonalBankAccount(charUuid, player.getUniqueID());
                if (personalAcc == null) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas de compte bancaire personnel."));
                    return;
                }

                double soldePerso = DBUtils.getAccountBalance(personalAcc);
                if (soldePerso < amount) {
                    player.sendMessage(new TextComponentString("§cFonds insuffisants sur votre compte personnel ! Solde : §e" + soldePerso + "€"));
                    return;
                }

                if (manager.deposit(company, charUuid, player.getUniqueID(), amount)) {
                    double newCompSolde = manager.getCompanyBalance(company);
                    player.sendMessage(new TextComponentString("§aVous avez déposé §e" + amount + "€ §asur le compte de l'entreprise §b" + company.getName() + " §a(Nouveau solde : §e" + newCompSolde + "€§a)."));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors du dépôt."));
                }
                break;
            }

            case "withdraw": {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/entreprise withdraw <montant>"));
                    return;
                }

                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                Rank myRank = company.getRankOf(charUuid);
                boolean canWithdraw = company.isOwner(charUuid) || (myRank != null && (myRank.isCanWithdraw() || myRank.getLevel() >= 80));
                if (!canWithdraw) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas l'autorisation de retirer des fonds de l'entreprise."));
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(args[1]);
                    if (amount <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    player.sendMessage(new TextComponentString("§cLe montant doit être un nombre positif !"));
                    return;
                }

                double compSolde = manager.getCompanyBalance(company);
                if (compSolde < amount) {
                    player.sendMessage(new TextComponentString("§cFonds insuffisants sur le compte de l'entreprise ! Solde : §e" + compSolde + "€"));
                    return;
                }

                String personalAcc = DBUtils.getPersonalBankAccount(charUuid, player.getUniqueID());
                if (personalAcc == null) {
                    player.sendMessage(new TextComponentString("§cVous n'avez pas de compte bancaire personnel pour recevoir l'argent."));
                    return;
                }

                if (manager.withdraw(company, charUuid, player.getUniqueID(), amount)) {
                    double newCompSolde = manager.getCompanyBalance(company);
                    player.sendMessage(new TextComponentString("§aVous avez retiré §e" + amount + "€ §adu compte de l'entreprise §b" + company.getName() + " §a(Nouveau solde entreprise : §e" + newCompSolde + "€§a)."));
                } else {
                    player.sendMessage(new TextComponentString("§cErreur lors du retrait."));
                }
                break;
            }

            case "solde": {
                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                double balance = manager.getCompanyBalance(company);
                player.sendMessage(new TextComponentString("§6[WesterLife] §aSolde de l'entreprise §b" + company.getName() + " §7(Compte n° §e" + company.getAccountNumber() + "§7) : §e" + balance + "€"));
                break;
            }

            case "paysalaries": {
                Company company = manager.getCompanyByEmployee(charUuid);
                if (company == null) {
                    player.sendMessage(new TextComponentString("§cVous n'êtes membre d'aucune entreprise."));
                    return;
                }

                if (!company.canManage(charUuid)) {
                    player.sendMessage(new TextComponentString("§cSeul le dirigeant ou manager peut déclencher le versement des salaires."));
                    return;
                }

                String result = manager.paySalaries(company, server);
                player.sendMessage(new TextComponentString("§6[WesterLife] " + result));
                break;
            }

            default:
                sendHelp(player);
                break;
        }
    }

    private void sendHelp(EntityPlayerMP player) {
        player.sendMessage(new TextComponentString("§6========== Système d'Entreprise - WesterLife =========="));
        player.sendMessage(new TextComponentString("§e/entreprise list §7- Afficher la liste de toutes les entreprises"));
        player.sendMessage(new TextComponentString("§e/entreprise info [nom|siret] §7- Afficher les détails d'une entreprise"));
        player.sendMessage(new TextComponentString("§e/entreprise my §7- Afficher les détails de votre entreprise"));
        player.sendMessage(new TextComponentString("§e/entreprise create <nom> [type] §7- Fonder une nouvelle entreprise"));
        player.sendMessage(new TextComponentString("§e/entreprise delete [nom] §7- Dissoudre votre entreprise"));
        player.sendMessage(new TextComponentString("§e/entreprise members §7- Lister les salariés et leur rang"));
        player.sendMessage(new TextComponentString("§e/entreprise hire <joueur> [rang] §7- Embaucher un joueur"));
        player.sendMessage(new TextComponentString("§e/entreprise fire <joueur> §7- Licencier un employé"));
        player.sendMessage(new TextComponentString("§e/entreprise setrank <joueur> <rang> §7- Modifier le rang d'un employé"));
        player.sendMessage(new TextComponentString("§e/entreprise leave §7- Démissionner de votre entreprise"));
        player.sendMessage(new TextComponentString("§e/entreprise setowner <joueur> §7- Transférer la direction"));
        player.sendMessage(new TextComponentString("§e/entreprise ranks §7- Lister les rangs et salaires"));
        player.sendMessage(new TextComponentString("§e/entreprise addrank <nom> <lvl> <salaire> §7- Créer un nouveau rang"));
        player.sendMessage(new TextComponentString("§e/entreprise delrank <nom> §7- Supprimer un rang"));
        player.sendMessage(new TextComponentString("§e/entreprise deposit <montant> §7- Déposer des fonds sur le compte"));
        player.sendMessage(new TextComponentString("§e/entreprise withdraw <montant> §7- Retirer des fonds"));
        player.sendMessage(new TextComponentString("§e/entreprise solde §7- Consulter la trésorerie"));
        player.sendMessage(new TextComponentString("§e/entreprise paysalaries §7- Verser les salaires à tous les employés"));
        player.sendMessage(new TextComponentString("§6======================================================="));
    }

    private String resolvePlayerName(MinecraftServer server, UUID uuid) {
        if (uuid == null) return "Inconnu";
        try {
            Character c = DBUtils.getCharacter(uuid);
            if (c != null && c.getFullName() != null && !c.getFullName().isEmpty() && !c.getFullName().contains("error")) {
                return c.getFullName();
            }
        } catch (Exception ignored) {}

        if (server != null) {
            EntityPlayerMP p = server.getPlayerList().getPlayerByUUID(uuid);
            if (p != null) return p.getName();
        }
        return uuid.toString().substring(0, 8);
    }

    private UUID findEmployeeUuid(MinecraftServer server, Company company, String query) {
        if (company == null || query == null || query.trim().isEmpty()) return null;
        query = query.trim().toLowerCase();

        // 1. By online player username
        if (server != null) {
            EntityPlayerMP online = server.getPlayerList().getPlayerByUsername(query);
            if (online != null) {
                Character c = DBUtils.getActiveCharacter(online);
                UUID targetUuid = (c != null) ? c.getUuid() : online.getUniqueID();
                if (company.hasEmployee(targetUuid)) {
                    return targetUuid;
                }
            }
        }

        // 2. By Employee Character display name or UUID
        for (Employee emp : company.getEmployees()) {
            if (emp.getCharacterUuid().toString().toLowerCase().startsWith(query)) {
                return emp.getCharacterUuid();
            }
            String disp = emp.getDisplayName(server);
            if (disp != null && disp.toLowerCase().contains(query)) {
                return emp.getCharacterUuid();
            }
        }

        return null;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "help", "list", "info", "my", "create", "delete", "hire", "fire", "setrank", "leave", "setowner", "members", "ranks", "addrank", "delrank", "deposit", "withdraw", "solde", "paysalaries");
        }

        CompanyManager manager = CompanyManager.getInstance();

        if (args.length == 2) {
            String sub = args[0].toLowerCase();
            switch (sub) {
                case "create":
                    return Collections.emptyList();
                case "info":
                case "delete":
                    List<String> compNames = new ArrayList<>();
                    for (Company c : manager.getAllCompanies()) {
                        compNames.add(c.getName());
                    }
                    return getListOfStringsMatchingLastWord(args, compNames);
                case "hire":
                case "setowner":
                    return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
                case "fire":
                case "setrank":
                    if (sender instanceof EntityPlayerMP) {
                        EntityPlayerMP p = (EntityPlayerMP) sender;
                        Character c = DBUtils.getActiveCharacter(p);
                        UUID charUuid = (c != null) ? c.getUuid() : p.getUniqueID();
                        Company comp = manager.getCompanyByEmployee(charUuid);
                        if (comp != null) {
                            List<String> empNames = new ArrayList<>();
                            for (Employee emp : comp.getEmployees()) {
                                empNames.add(emp.getDisplayName(server));
                            }
                            return getListOfStringsMatchingLastWord(args, empNames);
                        }
                    }
                    return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
                case "delrank":
                    if (sender instanceof EntityPlayerMP) {
                        EntityPlayerMP p = (EntityPlayerMP) sender;
                        Character c = DBUtils.getActiveCharacter(p);
                        UUID charUuid = (c != null) ? c.getUuid() : p.getUniqueID();
                        Company comp = manager.getCompanyByEmployee(charUuid);
                        if (comp != null) {
                            List<String> rankNames = comp.getRanks().stream().map(Rank::getName).collect(Collectors.toList());
                            return getListOfStringsMatchingLastWord(args, rankNames);
                        }
                    }
                    break;
            }
        }

        if (args.length == 3) {
            String sub = args[0].toLowerCase();
            if (sub.equalsIgnoreCase("create")) {
                List<String> types = Arrays.stream(CompanyType.values()).map(CompanyType::name).collect(Collectors.toList());
                return getListOfStringsMatchingLastWord(args, types);
            }
            if (sub.equalsIgnoreCase("hire") || sub.equalsIgnoreCase("setrank")) {
                if (sender instanceof EntityPlayerMP) {
                    EntityPlayerMP p = (EntityPlayerMP) sender;
                    Character c = DBUtils.getActiveCharacter(p);
                    UUID charUuid = (c != null) ? c.getUuid() : p.getUniqueID();
                    Company comp = manager.getCompanyByEmployee(charUuid);
                    if (comp != null) {
                        List<String> rankNames = comp.getRanks().stream().map(Rank::getName).collect(Collectors.toList());
                        return getListOfStringsMatchingLastWord(args, rankNames);
                    }
                }
            }
        }

        return Collections.emptyList();
    }
}
