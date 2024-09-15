package com.westernyey.Flopy.ui.cardModel;

public class CardModel {
    private final String name_and_age;
    private final String city;
    private final String distance;
    private final String target;
    private final int imageResourceId;

    public CardModel(String name_and_age, String city, String distance, String target, int imageResourceId) {
        this.name_and_age = name_and_age;
        this.city = city;
        this.distance = distance;
        this.target = target;
        this.imageResourceId = imageResourceId;
    }

    public String getNameAndAge() {
        return name_and_age;
    }
    public String getCity() {return city;}
    public String getDistance() {return distance;}
    public String getTarget() {return target;}

    public int getImageResourceId() {
        return imageResourceId;
    }
}
