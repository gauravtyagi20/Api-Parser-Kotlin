package com.fourtek.apiparser

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by fourtek on 28/2/18.
 */
interface ResponseKeyAPI {

   /* *//*Login API *//*
    @POST("login")
    @FormUrlEncoded
    abstract fun login(@Field("email") email: String): Call<UserDetailResponse>*/
   @FormUrlEncoded
   @POST("mobileapi/customer/login")
   abstract fun getLogin(@Field("email") email: String,
                             @Field("deviceid") deviceid: String,
                             @Field("devicetype") devicetype: String,
                             @Field("password") password: String): Call<UserDetailResponse>


}