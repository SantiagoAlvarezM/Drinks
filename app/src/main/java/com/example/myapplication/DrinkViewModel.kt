package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.DrinksApi
import com.example.myapplication.model.DrinksActions
import com.example.myapplication.util.ApiResult
import com.example.myapplication.util.toUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val API_REQUEST_DELAY = 100L

class DrinkViewModel : ViewModel() {

    private val api = DrinksApi

    private val _actions = MutableLiveData<DrinksActions>()
    val actions: LiveData<DrinksActions> = _actions

    fun searchDrinks(query: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                api.getDrink(query)
            }
            when (result) {
                is ApiResult.Error -> _actions.postValue(DrinksActions.Error)
                is ApiResult.Success -> {
                    val data = result.data.toUIModel()
                    _actions.postValue(DrinksActions.DataLoaded(data))
                }
            }
        }
    }
}