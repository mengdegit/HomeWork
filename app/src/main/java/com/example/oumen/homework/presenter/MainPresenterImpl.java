package com.example.oumen.homework.presenter;

import com.example.oumen.homework.view.MainView;


public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }


    @Override
    public void loadDetialHome() {
        mMainView.hideButton();
        mMainView.showProgress();
        mMainView.loadData();
    }

    @Override
    public void refreshDetailHome() {

    }

    @Override
    public void loadComplete() {
        mMainView.hideProgress();
    }
}
