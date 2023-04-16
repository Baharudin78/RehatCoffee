package com.rehat.rehatcoffee.presentation.admin.orderan

import androidx.lifecycle.ViewModel
import com.rehat.rehatcoffee.domain.adminorder.usecase.GetAdminOrderUseCase
import com.rehat.rehatcoffee.domain.adminorder.usecase.UpdateAdminOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderAdminViewModel @Inject constructor(
    private val getAdminOrderUseCase: GetAdminOrderUseCase,
    private val updateAdminOrderUseCase: UpdateAdminOrderUseCase
) : ViewModel(){


}