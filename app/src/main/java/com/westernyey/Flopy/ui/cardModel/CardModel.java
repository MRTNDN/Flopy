package com.westernyey.Flopy.ui.cardModel;

import java.util.List;

public class CardModel {
    private final String nameAndAge; // Переименовал переменные для использования CamelCase
    private final String city;
    private final String distance;
    private final String target;
    private final List<Integer> imageResourceIds; // Список изображений
    private final List<String> additionalInfo; // Список дополнительной информации
    private int currentImageIndex = 0; // Индекс текущего изображения

    public CardModel(String nameAndAge, String city, String distance, String target,
                     List<Integer> imageResourceIds, List<String> additionalInfo) {
        this.nameAndAge = nameAndAge;
        this.city = city;
        this.distance = distance;
        this.target = target;
        this.imageResourceIds = imageResourceIds;
        this.additionalInfo = additionalInfo; // Инициализация списка дополнительной информации
    }

    public String getNameAndAge() {
        return nameAndAge;
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

    public int getCurrentImageResourceId() {
        return imageResourceIds.get(currentImageIndex);
    }

    public List<String> getAdditionalInfo() {
        return additionalInfo; // Метод для получения дополнительной информации
    }

    // Метод для перехода к следующему изображению
    public void nextImage() {
        if (currentImageIndex < imageResourceIds.size() - 1) {
            currentImageIndex++;
        }
    }

    // Метод для перехода к предыдущему изображению
    public void previousImage() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
        }
    }
}
