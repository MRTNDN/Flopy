package com.westernyey.Flopy.ui.cardModel;

public class CardModel {
    private String text;
    private int imageResourceId;

    public CardModel(String text, int imageResourceId) {
        this.text = text;
        this.imageResourceId = imageResourceId;
    }

    public String getText() {
        return text;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
