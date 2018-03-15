package com.davrbk.movatest;

import android.app.Application;

import io.realm.Realm;

public class MovaTest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitSingleton.init();
        Realm.init(this);
    }
}
