package fr.yan36.westerlife.common.objects.entreprises;

import java.util.UUID;

public class CompanyBase extends Company {

    public CompanyBase(String companyIdentifiant, String name, Integer creationDate, String creator) {
        super(0, companyIdentifiant, name, CompanyType.SARL, parseUuidSafe(creator), "0", 0.0, String.valueOf(creationDate));
    }

    private static UUID parseUuidSafe(String str) {
        try {
            if (str != null && str.length() == 36) {
                return UUID.fromString(str);
            }
        } catch (Exception ignored) {}
        return UUID.randomUUID();
    }

    public String getCompanyIdentifiant() {
        return getSiret();
    }

    public void setCompanyIdentifiant(String companyIdentifiant) {
        setSiret(companyIdentifiant);
    }

    public Integer getCreationDateAsInt() {
        try {
            return Integer.parseInt(getCreationDate());
        } catch (Exception e) {
            return 0;
        }
    }

    public void setCreationDate(Integer creationDate) {
        super.setCreationDate(String.valueOf(creationDate));
    }

    public String getCreator() {
        return getOwnerUuid() != null ? getOwnerUuid().toString() : "";
    }

    public void setCreator(String creator) {
        setOwnerUuid(parseUuidSafe(creator));
    }
}
