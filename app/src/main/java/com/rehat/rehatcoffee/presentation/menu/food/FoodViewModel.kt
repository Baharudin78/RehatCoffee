package com.rehat.rehatcoffee.presentation.menu.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CartIndicatorResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.cart.usecase.CartIndicatorUseCase
import com.rehat.rehatcoffee.domain.cart.usecase.CreateCartUseCase
import com.rehat.rehatcoffee.domain.cart.usecase.DeleteCartUseCase
import com.rehat.rehatcoffee.domain.cart.usecase.UpdateCartUseCase
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.menu.usecase.MenuUseCase
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.presentation.register.RegisterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val getFoodUseCase: MenuUseCase,
    private val createCartUseCase: CreateCartUseCase,
    private val cartIndicatorUseCase: CartIndicatorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GetMenuFoodViewState>(GetMenuFoodViewState.Init)
    val state: StateFlow<GetMenuFoodViewState> get() = _state

    private val _foods = MutableStateFlow<List<MenuEntity>>(emptyList())
    val foods: StateFlow<List<MenuEntity>> get() = _foods

    private val _cart = MutableStateFlow<CartViewState>(CartViewState.Init)
    val cart: StateFlow<CartViewState> get() = _cart

    private val _cartIndicatorState =
        MutableStateFlow<CartIndicatorViewState>(CartIndicatorViewState.Init)
    val cartIndicatorState: StateFlow<CartIndicatorViewState> get() = _cartIndicatorState

    private val _cartCount = MutableStateFlow<CartIndicatorEntity?>(null)
    val cartCount: StateFlow<CartIndicatorEntity?> get() = _cartCount

    private fun setLoading() {
        _state.value = GetMenuFoodViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = GetMenuFoodViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = GetMenuFoodViewState.ShowToast(message)
    }

    private fun setCartLoading() {
        _cart.value = CartViewState.IsLoading(true)
    }

    private fun hideCartLoading() {
        _cart.value = CartViewState.IsLoading(false)
    }

    private fun setCartCountLoading() {
        _cartIndicatorState.value = CartIndicatorViewState.IsLoading(true)
    }

    private fun hideCartCountLoading() {
        _cartIndicatorState.value = CartIndicatorViewState.IsLoading(false)
    }

    private fun showCartToast(message: String) {
        _cart.value = CartViewState.ShowToast(message)
    }

    fun fetchMenuFood() {
        viewModelScope.launch {
            getFoodUseCase.getMenuFood()
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
                            _foods.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

    fun fetchMenuDrink() {
        viewModelScope.launch {
            getFoodUseCase.getMenuDrink()
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
                            _foods.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

    fun createCart(productId: String) {
        viewModelScope.launch {
            createCartUseCase.createCart(productId)
                .onStart {
                    setCartLoading()
                }
                .catch { exception ->
                    hideCartLoading()
                    showToast(exception.message ?: "Error Occured")
                }
                .collect {
                    handleCreateCart(it)
                }
        }
    }

    fun getCartIndicator() {
        viewModelScope.launch {
            cartIndicatorUseCase.getCartIndicator()
                .onStart {
                    setCartCountLoading()
                }
                .catch { exception ->
                    hideCartCountLoading()
                    showToast(exception.message ?: "Error occured")
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

    private fun handleCreateCart(baseResult: BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _cart.value =
                CartViewState.Error(baseResult.rawResponse)
            is BaseResult.Success -> _cart.value =
                CartViewState.SuccessCreateCart(baseResult.data)
        }
    }

    private fun handleUpdateCart(baseResult: BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _cart.value =
                CartViewState.Error(baseResult.rawResponse)
            is BaseResult.Success -> _cart.value =
                CartViewState.SuccessUpdateCart(baseResult.data)
        }
    }



}

sealed class GetMenuFoodViewState {
    object Init : GetMenuFoodViewState()
    data class IsLoading(val isLoading: Boolean) : GetMenuFoodViewState()
    data class ShowToast(val message: String) : GetMenuFoodViewState()
}

sealed class CartViewState {
    object Init : CartViewState()
    data class IsLoading(val isLoading: Boolean) : CartViewState()
    data class ShowToast(val message: String) : CartViewState()
    data class SuccessCreateCart(val cartDataEntity: CartDataEntity) : CartViewState()
    data class SuccessUpdateCart(val cartDataEntity: CartDataEntity) : CartViewState()
    data class SuccessDeleteCart(val cartDataEntity: CartDataEntity) : CartViewState()
    data class Error(val rawResponse: WrappedResponse<CartDataResponse>) :
        CartViewState()
}

sealed class CartIndicatorViewState {
    object Init : CartIndicatorViewState()
    data class IsLoading(val isLoading: Boolean) : CartIndicatorViewState()
    data class ShowToast(val message: String) : CartIndicatorViewState()
    data class SuccessCartCount(val cartIndicatorEntity: CartIndicatorEntity) :
        CartIndicatorViewState()
    data class Error(val rawResponse: WrappedResponse<CartIndicatorResponse>) :
        CartIndicatorViewState()
}