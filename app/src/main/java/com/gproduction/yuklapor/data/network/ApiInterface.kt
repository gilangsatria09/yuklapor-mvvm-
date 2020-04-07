package com.gproduction.yuklapor.data.network

import com.gproduction.yuklapor.data.network.model.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email")email:String,
        @Field("password")password:String
    ) : Call<LoginResponse>

    companion object{
        operator fun invoke() : ApiInterface{
            return Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}