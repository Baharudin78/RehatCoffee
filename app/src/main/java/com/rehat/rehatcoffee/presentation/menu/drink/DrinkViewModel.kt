package com.rehat.rehatcoffee.presentation.menu.drink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.usecase.MenuUseCase
import com.rehat.rehatcoffee.presentation.menu.food.GetMenuFoodViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkViewModel @Inject constructor(
    private val getMenuUseCase: MenuUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GetMenuDrinkViewState>(GetMenuDrinkViewState.Init)
    val state: StateFlow<GetMenuDrinkViewState> get() = _state

    private val _drinks = MutableStateFlow<List<MenuEntity>>(emptyList())
    val drinks: StateFlow<List<MenuEntity>> get() = _drinks

    private fun setLoading(){
        _state.value = GetMenuDrinkViewState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = GetMenuDrinkViewState.IsLoading(false)
    }

    private fun showToast(message: String){
        _state.value = GetMenuDrinkViewState.ShowToast(message)
    }
    init {
        fetchMenuDrink()
    }

    fun fetchMenuDrink() {
        viewModelScope.launch {
            getMenuUseCase.getMenuDrink()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message ?: "Error No Found")
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            _drinks.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }
}

sealed class GetMenuDrinkViewState {
    object Init : GetMenuDrinkViewState()
    data class IsLoading(val isLoading: Boolean) : GetMenuDrinkViewState()
    data class ShowToast(val message: String) : GetMenuDrinkViewState()
}