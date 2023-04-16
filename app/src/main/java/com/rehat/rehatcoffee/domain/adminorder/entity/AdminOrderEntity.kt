package com.rehat.rehatcoffee.domain.adminorder.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminProductOrderResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminOrderEntity(
    val id: String,
    val orderStatus: Boolean,
    val payStatus: Boolean,
    val product: List<AdminProductOrderEntity>,
    val totalPrice: Int,
) : Parcelable
