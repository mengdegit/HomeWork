package com.example.oumen.homework.bean;

import java.util.ArrayList;

/**
 * Created by oumen on 2016/6/14.
 */
public class Home {
    private User user;
    private ArrayList<Photo> photos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Home{" +
                "user=" + user +
                ", photos=" + photos +
                '}';
    }
}
