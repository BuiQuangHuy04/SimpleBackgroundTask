package com.example.btvn;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiListener {
    private static final String url = "https://gorest.co.in/public/v2/";

    private static Retrofit retrofit;
    public static ApiInterface getAPI() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit.create(ApiInterface.class);
    }
}
