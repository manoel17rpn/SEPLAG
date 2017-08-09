package br.com.seplag.controller;

import br.com.seplag.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Manoel Neto on 29/04/2017.
 */

public interface UserController {

    @POST("users/new")
    Call<UserModel> createUser(@Body UserModel user);

    @GET("users/{phone}")
    Call<UserModel> getUser(@Path("phone") String phone);

    @GET("users/{phone}")
    Call<Boolean> verifyNumber(@Path("phone") String phone);

    @FormUrlEncoded
    @PUT("users/updatescore")
    Call<UserModel> updateUserScore(@Field("score") String score);

    @FormUrlEncoded
    @PUT("users/updatescore")
    Call<UserModel> updateUser(@Field("sex") String sex, @Field("scholarity") String scholarity,
                               @Field("number_residents") String number_residents,
                               @Field("income") String income);
}
