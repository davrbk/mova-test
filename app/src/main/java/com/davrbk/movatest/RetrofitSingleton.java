package com.davrbk.movatest;

import android.util.Log;

import com.davrbk.movatest.api.ImagesResponse;
import com.davrbk.movatest.api.MovaApi;
import com.davrbk.movatest.model.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class RetrofitSingleton {
    private static final String TAG = RetrofitSingleton.class.getSimpleName();
    private static final String BASE_URL = "https://api.gettyimages.com/v3/search/";

    private static Observable<ImagesResponse> observableRetrofit;
    private static BehaviorSubject<ImagesResponse> observableModelsList;
    private static Subscription subscription;
    private static MovaApi api;

    private RetrofitSingleton() {
    }

    public static void init() {
        Log.d(TAG, "init");

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();
        api = retrofit.create(MovaApi.class);
    }

    public static void resetModelsObservable(String search) {
        observableModelsList = BehaviorSubject.create();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = api.getImagesList(search).subscribe(new Subscriber<ImagesResponse>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                observableModelsList.onError(e);
            }

            @Override
            public void onNext(ImagesResponse models) {
                observableModelsList.onNext(models);
            }
        });
    }

    public static Observable<ImagesResponse> getImagesObservable(String search) {
        if (observableModelsList == null) {
            resetModelsObservable(search);
        }
        return observableModelsList;
    }
}
