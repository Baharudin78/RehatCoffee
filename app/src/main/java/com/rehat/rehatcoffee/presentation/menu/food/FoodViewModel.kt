package com.rehat.rehatcoffee.presentation.menu.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.usecase.MenuUseCase
import com.rehat.rehatcoffee.presentation.menu.drink.GetMenuDrinkViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val getFoodUseCase: MenuUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GetMenuFoodViewState>(GetMenuFoodViewState.Init)
    val state: StateFlow<GetMenuFoodViewState> get() = _state

    private val _foods = MutableStateFlow<List<MenuEntity>>(emptyList())
    val foods: StateFlow<List<MenuEntity>> get() = _foods

    fun fetchMenuFood() {
        viewModelScope.launch {
            getFoodUseCase.getMenuFood()
                .onStart { _state.value = GetMenuFoodViewState.IsLoading(true) }
                .catch { exception ->
                    _state.value = GetMenuFoodViewState.IsLoading(false)
                    _state.value =
                        GetMenuFoodViewState.ShowToast(exception.message ?: "An error occurred")
                }
                .collect { result ->
                    _state.value = GetMenuFoodViewState.IsLoading(false)
                    when (result) {
                        is BaseResult.Success -> {
                            _foods.value = result.data
                        }
                        is BaseResult.Error -> {
                            _state.value =
                                GetMenuFoodViewState.ShowToast(result.rawResponse.message ?: "")
                        }
                    }
                }
        }
    }

}

sealed class GetMenuFoodViewState {
    object Init : GetMenuFoodViewState()
    data class IsLoading(val isLoading: Boolean) : GetMenuFoodViewState()
    data class ShowToast(val message: String) : GetMenuFoodViewState()
}