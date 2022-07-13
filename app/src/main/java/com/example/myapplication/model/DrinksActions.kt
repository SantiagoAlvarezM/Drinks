package com.example.myapplication.model

sealed class DrinksActions {
    object Error : DrinksActions()
    class DataLoaded(val data: List<DrinkItemUI>) : DrinksActions()
}
