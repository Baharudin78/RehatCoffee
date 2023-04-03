package com.rehat.rehatcoffee.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterRequest
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.domain.register.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterViewState>(RegisterViewState.Init)
    val state: StateFlow<RegisterViewState> get() = _state

    private fun setLoading() {
        _state.value = RegisterViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = RegisterViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = RegisterViewState.ShowToast(message)
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.execute(registerRequest)
                .onStart { showLoading() }
                .catch { exception -> handleException(exception) }
                .collect { handleResult(it) }
        }
    }

    private fun showLoading() {
        setLoading()
    }

    private fun handleException(exception: Throwable) {
        hideLoading()
        showToast(exception.message.toString())
    }

    private fun handleResult(baseResult: BaseResult<RegisterEntity, WrappedResponse<RegisterResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _state.value =
                RegisterViewState.ErrorRegister(baseResult.rawResponse)
            is BaseResult.Success -> _state.value =
                RegisterViewState.SuccessRegister(baseResult.data)
        }
    }
}

sealed class RegisterViewState {
    object Init : RegisterViewState()
    data class IsLoading(val isLoading: Boolean) : RegisterViewState()
    data class ShowToast(val message: String) : RegisterViewState()
    data class SuccessRegister(val registerEntity: RegisterEntity) : RegisterViewState()
    data class ErrorRegister(val rawResponse: WrappedResponse<RegisterResponse>) :
        RegisterViewState()
}