package com.rehat.rehatcoffee.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.login.remote.dto.LoginRequest
import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.domain.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Init)
    val state: StateFlow<LoginViewState> get() = _state

    private fun setLoading() {
        _state.value = LoginViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = LoginViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = LoginViewState.ShowToast(message)
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
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

    private fun handleResult(baseResult: BaseResult<LoginEntity, WrappedResponse<LoginResponse>>) {
        hideLoading()
        when (baseResult) {
            is BaseResult.Error -> _state.value = LoginViewState.ErrorLogin(baseResult.rawResponse)
            is BaseResult.Success -> _state.value = LoginViewState.SuccessLogin(baseResult.data)
        }
    }

}

sealed class LoginViewState {
    object Init : LoginViewState()
    data class IsLoading(val isLoading: Boolean) : LoginViewState()
    data class ShowToast(val message: String) : LoginViewState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginViewState()
    data class ErrorLogin(val rawResponse: WrappedResponse<LoginResponse>) : LoginViewState()
}