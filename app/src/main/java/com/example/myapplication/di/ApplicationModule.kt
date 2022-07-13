package com.example.myapplication.di

import com.example.myapplication.Config.API_BASE_URL
import com.example.myapplication.data.api.ApiService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  This is the main module provides app wide dependencies, usually with Dagger ot other framework
 */
object ApplicationModule {

    fun providesApiService(): ApiService {
        val gson = Gson().apply {
            serializeNulls()
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}