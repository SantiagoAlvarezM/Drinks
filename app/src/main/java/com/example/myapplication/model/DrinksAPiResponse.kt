package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class DrinksAPiResponse(
    @SerializedName("drinks")
    val drinks: List<Drink?>?
)