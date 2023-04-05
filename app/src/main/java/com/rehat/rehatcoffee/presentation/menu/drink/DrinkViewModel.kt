package com.rehat.rehatcoffee.presentation.menu.drink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.usecase.MenuUseCase
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

    init {
        fetchMenuDrink()
    }

    fun fetchMenuDrink() {
        viewModelScope.launch {
            getMenuUseCase.getMenuDrink()
                .onStart { _state.value = GetMenuDrinkViewState.IsLoading(true) }
                .catch { exception ->
                    _state.value = GetMenuDrinkViewState.IsLoading(false)
                    _state.value =
                        GetMenuDrinkViewState.ShowToast(exception.message ?: "An error occurred")
                }
                .collect { result ->
                    _state.value = GetMenuDrinkViewState.IsLoading(false)
                    when (result) {
                        is BaseResult.Success -> {
                            _drinks.value = result.data
                        }
                        is BaseResult.Error -> {
                            _state.value =
                                GetMenuDrinkViewState.ShowToast(result.rawResponse.message ?: "")
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