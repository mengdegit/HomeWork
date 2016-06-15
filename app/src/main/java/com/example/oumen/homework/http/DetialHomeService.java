package com.example.oumen.homework.http;

import com.example.oumen.homework.bean.Post;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface DetialHomeService {

    @GET("query/home")
    Observable<Post> getHome();
}
