package com.example.btvn;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    String token = "62b63942273074ad7ffe2685d86f5c9d9b350de1f9caed491f5995c90c1167ca";

    @GET("users?access-token=" + token)
    Call<ArrayList<User>> getAllUsers();

    @POST("users?access-token=" + token)
    Call<User> postUser(@Body User user);

    @PUT("users/{id}?access-token=" + token)
    Call<User> putUser(@Body User user, @Path("id") int id);

    @DELETE("users/{id}?access-token=" + token)
    Call<User> deleteUser(@Path("id") int id);
}
