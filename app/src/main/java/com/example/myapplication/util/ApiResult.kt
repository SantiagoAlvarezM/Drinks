package com.example.myapplication.util

import com.example.myapplication.model.ApiFailure

sealed class ApiResult<out R> {

    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val data: ApiFailure, val code: Int) : ApiResult<Nothing>()
}
