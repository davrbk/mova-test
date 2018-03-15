package com.davrbk.movatest.presenter;

import com.davrbk.movatest.model.Image;

public interface MovaContract {

    interface Presenter {
        void init();
        Image onItem(int position);
        int count();
        void searchImage(String phrase);
        void onDestroy();
    }

    interface View {
        void invalidateList();
    }
}
