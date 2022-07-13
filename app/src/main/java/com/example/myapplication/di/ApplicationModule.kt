package com.example.myapplication.di

import com.example.myapplication.Config.API_BASE_URL
import com.example.myapplication.data.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  This is the main module provides app wide dependencies, usually with Dagger ot other framework
 */
object ApplicationModule {

    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }
}