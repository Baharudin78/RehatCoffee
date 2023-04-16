package com.rehat.rehatcoffee.domain.adminorder.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminProductItemOrderResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminProductOrderEntity(
    val id: String? = null,
    val product: AdminProductItemOrderEntity? = null,
    val qty: Int? = null,
    val totalPrice: Int? = null,
) : Parcelable
