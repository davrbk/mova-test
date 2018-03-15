package com.davrbk.movatest.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Image extends RealmObject {

    public final static String ID = "id";
    @PrimaryKey
    @Expose
    @SerializedName("id")
    String id;
    @Expose
    @SerializedName("display_sizes")
    RealmList<DisplaySizes> displaySizes;
    //    @Expose
//    @SerializedName("uri")
//    String mUri;
    @Expose
    @SerializedName("title")
    String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<DisplaySizes> getDisplaySizes() {
        return displaySizes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    public String getUri() {
//        return mUri;
//    }
//
//    public void setUri(String mUri) {
//        this.mUri = mUri;
//    }
}
