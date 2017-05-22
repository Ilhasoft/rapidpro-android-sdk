package io.rapidpro.sdk.core.models;

/**
 * Created by johncordeiro on 11/11/15.
 */
public class CustomLocale {

    private String iso3Language;

    private String displayLanguage;

    public CustomLocale(String iso3Language, String displayLanguage) {
        this.iso3Language = iso3Language;
        this.displayLanguage = displayLanguage;
    }

    public String getIso3Language() {
        return iso3Language;
    }

    public void setIso3Language(String iso3Language) {
        this.iso3Language = iso3Language;
    }

    public String getDisplayLanguage() {
        return displayLanguage;
    }

    public void setDisplayLanguage(String displayLanguage) {
        this.displayLanguage = displayLanguage;
    }

    @Override
    public String toString() {
        return displayLanguage;
    }
}
