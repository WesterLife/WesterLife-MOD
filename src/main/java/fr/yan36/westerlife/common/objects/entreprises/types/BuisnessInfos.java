package fr.yan36.westerlife.common.objects.entreprises.types;

public class BuisnessInfos {
    public enum Type {
        SAS,
        SARL,
        SA,
        SELARL,
        ASSOCIATION,
        PUBLIQUE,
        MICRO;

        public static Type fromCompanyType(fr.yan36.westerlife.common.objects.entreprises.CompanyType type) {
            if (type == null) return SARL;
            try {
                return valueOf(type.name());
            } catch (Exception e) {
                return SARL;
            }
        }
    }
}
