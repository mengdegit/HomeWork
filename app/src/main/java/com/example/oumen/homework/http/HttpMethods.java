package com.example.oumen.homework.http;


import android.util.Log;

import com.example.oumen.homework.bean.Home;
import com.example.oumen.homework.bean.Photo;
import com.example.oumen.homework.bean.Post;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *  以Retrofit方式进行网络请求
 */
public class HttpMethods {

    public static final String BASE_URL = "http://www.playalot.cn/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private DetialHomeService homeService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())  // 以Gson方式进行转化
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        homeService = retrofit.create(DetialHomeService.class); // 进行网络请求
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用来获取主页的数据
     *
     * @param subscriber 观察者
     */
    public void getHomeList(Subscriber<List<Home>> subscriber) {
        rx.Observable observable = homeService.getHome()   // 得到可以观察的对象
                .map(new Func1<Post, List<Home>>() {
                    @Override
                    public List<Home> call(Post post) {
                        return post.getPosts();   // 这里没有采用moshi进行json解析是因为retrofit已经对其进行解析满足我们的需要了
                    }   // 将字符串转化成最终所需要的对象

                }); // 将对象进行转化
        toSubscribe(observable, subscriber);

    }

    private <T> void toSubscribe(rx.Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())  // 对可观察者进行订阅
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
