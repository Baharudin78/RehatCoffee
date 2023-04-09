package com.rehat.rehatcoffee.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity
import com.rehat.rehatcoffee.domain.notificaitation.usecase.GetNotifUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotifUseCase: GetNotifUseCase
): ViewModel(){

    private val _state = MutableStateFlow<NotifViewState>(NotifViewState.Init)
    val state: StateFlow<NotifViewState> get() = _state

    private val _notif = MutableStateFlow<List<NotificationEntity>>(emptyList())
    val notif: StateFlow<List<NotificationEntity>> get() = _notif

    private fun setLoading() {
        _state.value = NotifViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = NotifViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        _state.value = NotifViewState.ShowToast(message)
    }

    fun fetchNotif() {
        viewModelScope.launch {
            getNotifUseCase.getNotif()
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
                            _notif.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message ?: "Error Occured")
                        }
                    }
                }
        }
    }
}

sealed class NotifViewState {
    object Init : NotifViewState()
    data class IsLoading(val isLoading: Boolean) : NotifViewState()
    data class ShowToast(val message: String) : NotifViewState()
}