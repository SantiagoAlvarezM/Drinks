package com.example.myapplication.data.api

import com.example.myapplication.di.ApplicationModule
import com.example.myapplication.model.ApiFailure
import com.example.myapplication.model.DrinksAPiResponse
import com.example.myapplication.util.ApiResult
import retrofit2.Response

/**
 * In charge of providing Drinks API bindings
 * Please note. this usually is a injectable class that extends from [ApiService]
 */
object DrinksApi {

    private val api = ApplicationModule.providesApiService()

    suspend fun getDrink(name: String): ApiResult<DrinksAPiResponse> {
        return api.getDrink(name).asApiResult()
    }

    private fun <T : Any?> Response<T>.asApiResult(): ApiResult<T> {
        return if (isSuccessful) {
            val body = body()
            body?.let {
                ApiResult.Success(it)
            } ?: apiError()
        } else {
            apiError()
        }
    }

    private fun <T : Any?> Response<T>.apiError(): ApiResult.Error {
        return ApiResult.Error(
            ApiFailure(errorBody()), code()
        )
    }
}