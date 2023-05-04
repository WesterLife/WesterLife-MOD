package fr.yan36.westerlife.common.objects.justice;

public class Punishment {

    public enum PunishmentType {
        FINE("Amende", "Paiement d'une somme définie appelée ammende", "Amende exprimée en €"),
        COMMUNITY_SERVICE("Travaux d'intérêt général", "Travaux non rémunérés au profit de la collectivité", "Nombre d'heures de travaux"),
        SUSPENDED_SENTENCE("Sursis", "Peine non exécutée si le condamné ne commet pas de nouvelle infraction", "Durée du sursis en mois"),
        PRISON("Prison", "Emprisonnement du condamné", "Durée de l'emprisonnement en mois"),
        PENAL_CONSTRAINT("Contrainte pénale", "Mesure de contrôle et de surveillance du condamné", "Durée de la contrainte pénale en mois"),
        PROVISORY_PRISON("Détention provisoire", "Emprisonnement du condamné en attendant son jugement", "N/A"),
        PROBATION("Liberté conditionnelle", "Mesure de contrôle et de surveillance du condamné", "Durée de la liberté conditionnelle en mois"),
        WITHDRAWAL_OF_DRIVING_LICENSE("Retrait de permis", "Retrait du permis de conduire du condamné", "Durée du retrait de permis en mois"),
        WITHDRAWAL_OF_RIGHT_TO_BE_ELECTED("Privation des droits civiques", "Privation des droits civiques", "Durée du retrait de la privation d'éligibilité en mois"),
        WITHDRAWAL_OF_POINTS_ON_DRIVING_LICENSE("Retrait de points sur le permis de conduire", "Retrait de points sur le permis de conduire du condamné", "Nombre de points retirés");

        private String name;
        private String description;
        private String amountMatch;

        PunishmentType(String name, String description, String amountMatch) {
            this.name = name;
            this.description = description;
            this.amountMatch = amountMatch;
        }

    }

    private PunishmentType type;
    private Float amount;
    private Boolean executed;

    Punishment(PunishmentType type, Float amount, Boolean executed) {
        this.type = type;
        this.amount = amount;
        this.executed = executed;
    }

    public PunishmentType getType() {
        return type;
    }

    public Float getAmount() {
        return amount;
    }

    public Boolean getExecuted() {
        return executed;
    }

}