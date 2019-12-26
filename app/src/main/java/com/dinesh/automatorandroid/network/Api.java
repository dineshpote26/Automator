package com.dinesh.automatorandroid.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    static final String BASE_URL = "http://demo0017680.mockable.io/";

    @POST
    Call<ResponseBody> getResponse(@Url String url);
}
