package com.rehat.rehatcoffee.presentation.admin.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.domain.product.usecase.DeleteProductUseCase
import com.rehat.rehatcoffee.domain.product.usecase.GetProductUseCase
import com.rehat.rehatcoffee.presentation.menu.food.GetMenuFoodViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<GetMenuViewState>(GetMenuViewState.Init)
    val state: StateFlow<GetMenuViewState> get() = _state

    private val _menu = MutableStateFlow<List<ProductEntity>>(emptyList())
    val menus: StateFlow<List<ProductEntity>> get() = _menu

    private fun setLoading() {
        _state.value = GetMenuViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = GetMenuViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = GetMenuViewState.ShowToast(message)
    }

    fun fetchMenuFood(kinds : String) {
        viewModelScope.launch {
            getProductUseCase.getProduct(kinds)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message ?: "Error Occured")
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            _menu.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

    fun fetchMenuDrink(kinds : String) {
        viewModelScope.launch {
            getProductUseCase.getProduct(kinds)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message ?: "Error Occured")
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            _menu.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

}

sealed class GetMenuViewState {
    object Init : GetMenuViewState()
    data class IsLoading(val isLoading: Boolean) : GetMenuViewState()
    data class ShowToast(val message: String) : GetMenuViewState()
}