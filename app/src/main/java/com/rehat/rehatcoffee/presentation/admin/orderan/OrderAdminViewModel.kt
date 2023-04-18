package com.rehat.rehatcoffee.presentation.admin.orderan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.usecase.GetAdminOrderUseCase
import com.rehat.rehatcoffee.domain.adminorder.usecase.UpdateAdminOrderUseCase
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.presentation.cart.CartViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderAdminViewModel @Inject constructor(
    private val getAdminOrderUseCase: GetAdminOrderUseCase,
    private val updateAdminOrderUseCase: UpdateAdminOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AdminOrderViewState>(AdminOrderViewState.Init)
    val state: StateFlow<AdminOrderViewState> get() = _state

    private val _order = MutableStateFlow<List<AdminOrderEntity>>(emptyList())
    val orders: StateFlow<List<AdminOrderEntity>> get() = _order

    private fun setLoading() {
        _state.value = AdminOrderViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = AdminOrderViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = AdminOrderViewState.ShowToast(message)
    }

    init {
        fetchOrder(true)
    }

    fun fetchOrder(orderStatus: Boolean) {
        viewModelScope.launch {
            getAdminOrderUseCase.getOrderAdmin(orderStatus)
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
                            _order.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }

    fun updateCart(
        id: String,
        orderStatus: Boolean,
        payStatus: Boolean
    ) {
        viewModelScope.launch {
            updateAdminOrderUseCase.updateOrderAdmin(id, orderStatus, payStatus)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message ?: "Error Occured")
                }
                .collect { result ->
                    handleUpdateOrder(result)
                }
        }
    }

    private fun handleUpdateOrder(baseResult: BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _state.value =
                AdminOrderViewState.Error(baseResult.rawResponse)
            is BaseResult.Success -> _state.value =
                AdminOrderViewState.SuccessUpdateOrder(baseResult.data)
        }
    }

}

sealed class AdminOrderViewState {
    object Init : AdminOrderViewState()
    data class IsLoading(val isLoading: Boolean) : AdminOrderViewState()
    data class ShowToast(val message: String) : AdminOrderViewState()
    data class SuccessUpdateOrder(val orderEntity: AdminOrderEntity) : AdminOrderViewState()
    data class Error(val rawResponse: WrappedResponse<AdminOrderResponse>) :
        AdminOrderViewState()
}