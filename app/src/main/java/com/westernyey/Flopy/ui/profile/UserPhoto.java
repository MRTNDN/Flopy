package com.westernyey.Flopy.ui.profile;

import androidx.annotation.NonNull;

import java.io.File;

public class UserPhoto {
    private String url = null; // URL фотографии (по умолчанию null)
    private File file = null;  // Локальный файл (по умолчанию null)
    private int dominating = -1; // Номер фото (-1 означает "не задано")

    // Конструктор по умолчанию
    public UserPhoto() {
        // Поля уже инициализированы значениями по умолчанию
    }

    // Конструктор с параметрами
    public UserPhoto(String url, File file, int dominating) {
        this.url = url;
        this.file = file;
        this.dominating = dominating;
    }

    // Геттеры и сеттеры
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public File getFile() { return file; }
    public void setFile(File file) { this.file = file; }

    public int getDominating() { return dominating; }
    public void setDominating(int dominating) { this.dominating = dominating; }

    @NonNull
    public String toString() {
        return "UserPhoto {" +
                "url='" + (url != null ? url : "Нет URL") + '\'' +
                ", file=" + (file != null ? file.getAbsolutePath() : "Нет файла") +
                ", dominating=" + (dominating != -1 ? dominating : "Не задано") +
                '}';
    }
}
