package com.example.myapplication.data.api

import com.example.myapplication.model.DrinksAPiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/json/v1/1/search.php")
    suspend fun getDrink(@Query("s") name: String): Response<DrinksAPiResponse>
}
