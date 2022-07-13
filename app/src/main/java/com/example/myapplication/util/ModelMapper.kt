package com.example.myapplication.util

import com.example.myapplication.model.DrinkItemUI
import com.example.myapplication.model.DrinksAPiResponse

fun DrinksAPiResponse.toUIModel(): List<DrinkItemUI> {
    return this.drinks?.asSequence()?.filterNotNull()?.map { apiItem ->
        DrinkItemUI(
            id = apiItem.idDrink.orEmpty(),
            name = apiItem.strDrink.orEmpty(),
            category = apiItem.strCategory.orEmpty(),
            tags = apiItem.strTags.orEmpty(),
            imageUrl = apiItem.strDrinkThumb.orEmpty()
        )
    }?.toList() ?: emptyList()
}