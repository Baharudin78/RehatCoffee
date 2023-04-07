package com.rehat.rehatcoffee.data.cart.remote.dto

data class CreateCartRequest(
    val productId : Int? = null,
    val qty : Int? = null
)
