package com.davrbk.movatest.api;


import com.davrbk.movatest.model.Image;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface MovaApi {
    @GET("images?fields=id,title,thumb&sort_order=best")
    @Headers("Api-Key: sdq2czngr6tjzqa2tkwg757r")
    Observable<ImagesResponse> getImagesList(@Query("phrase") String searchPhrase);
}
