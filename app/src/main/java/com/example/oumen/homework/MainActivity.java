package com.example.oumen.homework;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.oumen.homework.adapter.HomeAdapter;
import com.example.oumen.homework.bean.Home;
import com.example.oumen.homework.http.HttpMethods;
import com.example.oumen.homework.presenter.MainPresenter;
import com.example.oumen.homework.presenter.MainPresenterImpl;
import com.example.oumen.homework.view.MainView;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MainActivity extends Activity implements MainView, View.OnClickListener {
    MainPresenter mainPresenter;  // 采用MVC的模式
    Button bt_load;
    ProgressBar pb;
    SwipeRefreshLayout srl;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager layoutManager;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_load = (Button) findViewById(R.id.bt);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        srl = (SwipeRefreshLayout) findViewById(R.id.sl);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        bt_load.setOnClickListener(this);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.refreshDetailHome();
                srl.setRefreshing(false);
            }
        });

        mainPresenter = new MainPresenterImpl(this);
    }


    @Override
    public void loadData() {
        // 采用RxJava  订阅的监听回调
        HttpMethods.getInstance().getHomeList(new Subscriber<List<Home>>() {
            @Override
            public void onCompleted() {
                mainPresenter.loadComplete();

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    Toast.makeText(MainActivity.this, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                } else if (e instanceof ConnectException) {
                    Toast.makeText(MainActivity.this, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNext(List<Home> homes) {
                if (homes != null && homes.size() > 0) {
                        if(layoutManager==null){
                            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter=new HomeAdapter((ArrayList<Home>) homes,MainActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                }

            }
        });
    }


    @Override
    public void refreshData() {

    }

    @Override
    public void showProgress() {
        pb.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        pb.setVisibility(View.GONE);

    }

    @Override
    public void hideButton() {
        bt_load.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                mainPresenter.loadDetialHome();
                break;
        }

    }
}
