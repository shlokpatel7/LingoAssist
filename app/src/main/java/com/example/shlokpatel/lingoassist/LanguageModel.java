package com.example.shlokpatel.lingoassist;

public class LanguageModel {
    boolean isSelected;
    String langName;

    public LanguageModel(boolean isSelected, String langName) {
        this.isSelected = isSelected;
        this.langName = langName;
    }

    public boolean isSelected() {
        return isSelected;
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
