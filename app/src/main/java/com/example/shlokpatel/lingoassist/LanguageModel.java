package com.example.shlokpatel.lingoassist;

public class LanguageModel {
    boolean isSelected;
    String langName;
    String localeCode;

    public LanguageModel(boolean isSelected, String langName, String localeCode) {
        this.isSelected = isSelected;
        this.langName = langName;
        this.localeCode = localeCode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
}
