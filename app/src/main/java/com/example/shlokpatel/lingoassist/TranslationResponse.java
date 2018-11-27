package com.example.shlokpatel.lingoassist;

public class TranslationResponse {
    String status;
    String translation;

    public TranslationResponse(String status, String translation) {
        this.status = status;
        this.translation = translation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
