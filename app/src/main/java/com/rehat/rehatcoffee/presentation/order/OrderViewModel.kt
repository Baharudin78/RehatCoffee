package com.rehat.rehatcoffee.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.usecase.GetOrderByIdUseCase
import com.rehat.rehatcoffee.domain.order.usecase.GetOrderUseCase
import com.rehat.rehatcoffee.presentation.cart.OrderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<OrderViewState>(OrderViewState.Init)
    val state : StateFlow<OrderViewState> get() = _state

    private val _order = MutableStateFlow<OrderEntity?>(null)
    val order : StateFlow<OrderEntity?> get() = _order

    private val _listOrder = MutableStateFlow<List<OrderEntity>>(emptyList())
    val listOrder: StateFlow<List<OrderEntity>> get() = _listOrder

    private fun setLoading(){
        _state.value = OrderViewState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = OrderViewState.IsLoading(false)
    }

    private fun showToast(message: String){
        _state.value = OrderViewState.ShowToast(message)
    }

    init {
        fetchOrder()
    }

    fun fetchOrder() {
        viewModelScope.launch {
            getOrderUseCase.getOrder()
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
                            _listOrder.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }
    fun getOrderDetai(id: String){
        viewModelScope.launch {
            getOrderByIdUseCase.getOrderDetail(id)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> {
                            _order.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message)
                        }
                    }
                }
        }
    }
}