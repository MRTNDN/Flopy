package com.westernyey.Flopy.ui.cardModel;

import java.util.List;

public class CardModel {
    private final int idPerson;
    private final String name;
    private final String age;
    private final String city;
    private final String distance;
    private final String target;
    private final List<String> photoUrls; // Список URL изображений
    private final List<String> additionalInfo;
    private int currentImageIndex = 0;

    public CardModel(int idPerson, String name, String age, String city, String distance, String target,
                     List<String> photoUrls, List<String> additionalInfo) {
        this.idPerson = idPerson;
        this.name = name;
        this.age = age;
        this.city = city;
        this.distance = distance;
        this.target = target;
        this.photoUrls = photoUrls;
        this.additionalInfo = additionalInfo;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getDistance() {
        return distance;
    }

    public String getTarget() {
        return target;
    }

    public String getCurrentImageUrl() {
        return photoUrls.get(currentImageIndex);
    }public int getCurrentImageIndex() {
        return currentImageIndex;
    }
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public List<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void nextImage() {
        if (currentImageIndex < photoUrls.size() - 1) {
            currentImageIndex++;
        }
    }

    public void previousImage() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
        }
    }

    public void resetImageIndex() {
        currentImageIndex = 0;
    }
}
