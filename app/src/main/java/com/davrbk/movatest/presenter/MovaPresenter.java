package com.davrbk.movatest.presenter;


import android.util.Log;

import com.davrbk.movatest.RetrofitSingleton;
import com.davrbk.movatest.api.ImagesResponse;
import com.davrbk.movatest.model.Image;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovaPresenter implements MovaContract.Presenter {

    private Subscription subscription;
    private List<Image> images;
    private Realm realm;
    private MovaContract.View view;

    public MovaPresenter(MovaContract.View view) {
        this.view = view;
        images = new ArrayList<>();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void init() {
        fetchFromRealm();
    }

    @Override
    public Image onItem(int position) {
        return images.get(position);
    }

    @Override
    public int count() {
        return images.size();
    }

    @Override
    public void searchImage(String phrase) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RetrofitSingleton.getImagesObservable(phrase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImagesResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Presenter", e.getMessage());
                    }

                    @Override
                    public void onNext(ImagesResponse images) {
                        List<Image> list = images.getImages();
                        if (MovaPresenter.this.images.contains(list.get(0))) {
                            return;
                        }
                        Image image = realm.where(Image.class)
                                .equalTo(Image.ID, list.get(0).getId())
                                .findFirst();
                        if (image != null) {
                            image.deleteFromRealm();
                            realm.commitTransaction();
                        }
                        MovaPresenter.this.images.add(list.get(0));
                        realm.beginTransaction();
                        realm.copyToRealm(list.get(0));
                        realm.commitTransaction();
                        view.invalidateList();
                    }
                });
    }

    @Override
    public void onDestroy() {
        realm.close();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void fetchFromRealm() {
        RealmResults<Image> images = realm.where(Image.class).findAll();
        this.images.addAll(images);
        view.invalidateList();
    }
}
