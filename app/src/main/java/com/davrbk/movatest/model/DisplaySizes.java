package com.davrbk.movatest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class DisplaySizes extends RealmObject {
    @Expose
    @SerializedName("uri")
    String mUri;

    public String getUri() {
        return mUri;
    }

    public void setUri(String mUri) {
        this.mUri = mUri;
    }
}
