package com.westernyey.Flopy.ui.settings.blackList;

public class BlacklistUser {
    private final String id;
    private final String name;
    private final int age;
    private final String photoUrl;

    public BlacklistUser(String id, String name, int age, String photoUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.photoUrl = photoUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhotoUrl() { return photoUrl; }
}

