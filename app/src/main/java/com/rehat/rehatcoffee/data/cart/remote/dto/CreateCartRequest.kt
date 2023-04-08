package com.rehat.rehatcoffee.data.cart.remote.dto

data class CreateCartRequest(
    val productId : String? = null,
    val qty : Int? = null
)
