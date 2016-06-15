package com.example.oumen.homework.bean;

import java.util.ArrayList;

/**
 * Created by oumen on 2016/6/14.
 */
public class Post {
    private ArrayList<Home> posts;

    public ArrayList<Home> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Home> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Post{" +
                "posts=" + posts +
                '}';
    }
}
