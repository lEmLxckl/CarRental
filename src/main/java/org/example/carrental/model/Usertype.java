package org.example.carrental.model;

public enum Usertype {
    DATAREGISTRERING("DATAREGISTRERING"),
    SKADE_OG_UDBEREDNING("SKADE OG UDBEREDNING"),
    FORRETNINGSUDVIKLERE("FORRETNINGSUDVIKLERE"),
    ADMIN("ADMIN");

    private String usertype;

    Usertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    @Override
    public String toString() {
        return this.usertype;
    }

    public static Usertype fromString(String usertype) {
        for (Usertype type : Usertype.values()) {
            if (type.usertype.equalsIgnoreCase(usertype)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Usertype.class.getCanonicalName() + "." + usertype);
    }
}
