package com.example.shlokpatel.lingoassist;

class MyText {
    String message;
    int viewType;

    public MyText(String message, int viewType) {
        this.message = message;
        this.viewType = viewType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
