package com.rehat.rehatcoffee.presentation.admin.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.usecase.MenuUseCase
import com.rehat.rehatcoffee.domain.product.usecase.CreateProductUseCase
import com.rehat.rehatcoffee.domain.product.usecase.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateMenuViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModel() {

    private val state = MutableStateFlow<CreateProductViewState>(CreateProductViewState.Init)
    val mState: StateFlow<CreateProductViewState> get() = state

    private fun setLoading() {
        state.value = CreateProductViewState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = CreateProductViewState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = CreateProductViewState.ShowToast(message)
    }

    private fun successCreate() {
        state.value = CreateProductViewState.SuccessCreate
    }

    fun uploadProduct(
        requestBodyMap: MutableMap<String, @JvmSuppressWildcards RequestBody>,
        letterPart: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            try {
                setLoading()
                createProductUseCase.createProduct(requestBodyMap, letterPart).collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            hideLoading()
                            successCreate()
                        }
                        is BaseResult.Error -> {
                            hideLoading()
                            showToast(result.rawResponse.message ?: "Unknown error")
                        }
                    }
                }
            } catch (exception: Exception) {
                hideLoading()
                showToast(exception.message ?: "Unknown error")
            }
        }
    }


    fun updateProduct(
        productId: String,
        requestBodyMap: MutableMap<String, @JvmSuppressWildcards RequestBody>,
        productPart: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            updateProductUseCase.updateProduct(productId, requestBodyMap, productPart)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            hideLoading()
                            successCreate()
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message)
                        }
                    }
                }
        }
    }
}

sealed class CreateProductViewState {
    object Init : CreateProductViewState()
    object SuccessCreate : CreateProductViewState()
    data class IsLoading(val isLoading: Boolean) : CreateProductViewState()
    data class ShowToast(val message: String) : CreateProductViewState()
    data class ErrorUpload(val rawResponse: WrappedResponse<ProductResponse>) :
        CreateProductViewState()

}