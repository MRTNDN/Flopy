package com.westernyey.Flopy.ui.settings.blackList;

public class BlacklistUser {
    private final String id;
    private final String name;
    private final int age;
    private final int photoResource;

    public BlacklistUser(String id, String name, int age, int photoResource) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoResource = photoResource;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getPhotoResource() { return photoResource; }
}
