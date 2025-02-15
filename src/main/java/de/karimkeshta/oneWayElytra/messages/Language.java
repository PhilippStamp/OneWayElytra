package de.karimkeshta.oneWayElytra.messages;

public enum Language {

    GERMAN("Deutsch", "de"),
    ENGLISH("English", "en"),
    SPANISH("Spanish", "es"),
    FRENCH("French", "fr"),
    ITALIAN("Italian", "it"),
    ;

    private String languageName;
    private String countryCode;

    private Language(String languageName, String countryCode) {
        this.languageName = languageName;
        this.countryCode = countryCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getCountryCode() {
        return countryCode;
    }

}
