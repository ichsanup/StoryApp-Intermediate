package com.dicoding.picodiploma.submissionaplikasistoryapp.api

import com.dicoding.picodiploma.submissionaplikasistoryapp.respones.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface API {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<ApiResponses>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<LoginResponses>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token : String,
    ):Call<AllStoriesResponses>

    @GET("stories")
    suspend fun getAllPaging(
        @Header("Authorization") token : String,
        @Query("page") page : Int,
        @Query("size") size : Int
    ):AllStoriesResponses

    @Multipart
    @POST("stories")
    fun postAddStories(
        @Header("Authorization") token: String,
        @Part file :MultipartBody.Part,
        @Part("description") description : RequestBody
    ):Call<ApiResponses>

    @GET("stories?location=1")
    fun getStoryBasedLocation(
        @Header("Authorization") token: String
    ): Call<AllStoriesResponses>
}