package com.rehat.rehatcoffee.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.cart.remote.dto.GetCartResponse
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.entity.GetCartEntity
import com.rehat.rehatcoffee.domain.cart.usecase.DeleteCartUseCase
import com.rehat.rehatcoffee.domain.cart.usecase.GetCartUseCase
import com.rehat.rehatcoffee.domain.cart.usecase.UpdateCartUseCase
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.usecase.CreateOrderUseCase
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.presentation.register.RegisterViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CartViewState>(CartViewState.Init)
    val state: StateFlow<CartViewState> get() = _state

    private val _cart = MutableStateFlow<GetCartEntity?>(null)
    val cart: StateFlow<GetCartEntity?> get() = _cart

    private val _deleteCartResult = MutableStateFlow<MessageResponse?>(null)
    val deleteCartResult: SharedFlow<MessageResponse?> = _deleteCartResult

    private val _orderState = MutableStateFlow<OrderViewState>(OrderViewState.Init)
    val orderState: StateFlow<OrderViewState> get() = _orderState

    private fun setOrderLoading() {
        _orderState.value = OrderViewState.IsLoading(true)
    }


    private fun hideOrderLoading() {
        _orderState.value = OrderViewState.IsLoading(false)
    }

    private fun showOrderToast(message: String) {
        _orderState.value = OrderViewState.ShowToast(message)
    }

    private fun setLoading() {
        _state.value = CartViewState.IsLoading(true)
    }


    private fun hideLoading() {
        _state.value = CartViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = CartViewState.ShowToast(message)
    }

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart()
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
                            _cart.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message)
                        }
                    }
                }
        }
    }

    fun updateCart(productId: String, qty: Int) {
        viewModelScope.launch {
            updateCartUseCase.updateCart(productId, qty)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message ?: "Error Occured")
                }
                .collect { result ->
                    handleUpdateCart(result)
                }
        }
    }

    fun deleteCart(productId: String) {
        viewModelScope.launch {
            deleteCartUseCase.deleteCart(productId)
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
                            _deleteCartResult.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

    fun createOrder() {
        viewModelScope.launch {
            createOrderUseCase.createOrder()
                .onStart {
                    setOrderLoading()
                }
                .catch { exception ->
                    handleOrderException(exception)
                }
                .collect {
                    handleOrderResult(it)
                }
        }
    }

    private fun handleOrderException(exception: Throwable) {
        hideOrderLoading()
        showOrderToast(exception.message.toString())
    }

    private fun handleUpdateCart(baseResult: BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _state.value =
                CartViewState.Error(baseResult.rawResponse)
            is BaseResult.Success -> _state.value =
                CartViewState.SuccessUpdateCart(baseResult.data)
        }
    }

    private fun handleOrderResult(baseResult: BaseResult<OrderEntity, WrappedResponse<OrderResponse>>) {
        hideOrderLoading()
        when (baseResult) {
            is BaseResult.Error -> _orderState.value =
                OrderViewState.ErrorOrder(baseResult.rawResponse)
            is BaseResult.Success -> _orderState.value =
                OrderViewState.SuccessOrder(baseResult.data)
        }
    }
}

sealed class CartViewState {
    object Init : CartViewState()
    data class IsLoading(val isLoading: Boolean) : CartViewState()
    data class ShowToast(val message: String) : CartViewState()
    data class SuccessUpdateCart(val cartDataEntity: CartDataEntity) : CartViewState()
    data class Error(val rawResponse: WrappedResponse<CartDataResponse>) :
        CartViewState()
}

sealed class OrderViewState {
    object Init : OrderViewState()
    data class IsLoading(val isLoading: Boolean) : OrderViewState()
    data class ShowToast(val message: String) : OrderViewState()
    data class SuccessOrder(val orderEntity: OrderEntity) : OrderViewState()
    data class ErrorOrder(val rawResponse: WrappedResponse<OrderResponse>) :
        OrderViewState()
}

