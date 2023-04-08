package com.rehat.rehatcoffee.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.cart.usecase.CartIndicatorUseCase
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.presentation.menu.food.CartIndicatorViewState
import com.rehat.rehatcoffee.presentation.menu.food.CartViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cartIndicatorUseCase: CartIndicatorUseCase
) : ViewModel() {

    private val _cartIndicatorState =
        MutableStateFlow<CartIndicatorViewState>(CartIndicatorViewState.Init)
    val cartIndicatorState: StateFlow<CartIndicatorViewState> get() = _cartIndicatorState

    private val _cartCount = MutableStateFlow<CartIndicatorEntity?>(null)
    val cartCount: StateFlow<CartIndicatorEntity?> get() = _cartCount

    private fun setCartCountLoading() {
        _cartIndicatorState.value = CartIndicatorViewState.IsLoading(true)
    }

    private fun hideCartCountLoading() {
        _cartIndicatorState.value = CartIndicatorViewState.IsLoading(false)
    }

    private fun showCartToast(message: String) {
        _cartIndicatorState.value = CartIndicatorViewState.ShowToast(message)
    }
    fun getCartIndicator() {
        viewModelScope.launch {
            cartIndicatorUseCase.getCartIndicator()
                .onStart {
                    setCartCountLoading()
                }
                .catch { exception ->
                    hideCartCountLoading()
                    showCartToast(exception.message ?: "Error Occured")
                }
                .collect{ result ->
                    hideCartCountLoading()
                    when(result){
                        is BaseResult.Success -> {
                            _cartCount.value = result.data
                        }
                        is BaseResult.Error -> {
                            showCartToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }
}