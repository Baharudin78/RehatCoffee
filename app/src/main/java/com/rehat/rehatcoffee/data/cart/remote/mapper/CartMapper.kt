package com.rehat.rehatcoffee.data.cart.remote.mapper

import com.rehat.rehatcoffee.data.cart.remote.dto.*
import com.rehat.rehatcoffee.domain.cart.entity.*


fun GetCartResponse.toGetCartEntity(): GetCartEntity {
    return GetCartEntity(
        cartData = convertCartDataResponseToEntity(cartData),
        totalPrice = totalPrice ?: 0
    )
}

fun CartDataResponse.toCartDataEntity(): CartDataEntity {
    return CartDataEntity(
        id = id,
        product = product?.toProductCartEntity(),
        qty = qty,
        totalPrice = totalPrice
    )
}

fun ProductCartResponse.toProductCartEntity(): ProductCartEntity {
    return ProductCartEntity(
        id = id,
        images = convertImageResponseToEntity(images),
        kinds = kinds,
        price = price,
        productName = productName
    )
}

fun CartIndicatorResponse.toCartIndicatorEntity(): CartIndicatorEntity{
    return CartIndicatorEntity(
        totalCart = totalCart
    )
}

private fun convertCartDataResponseToEntity(cartData: List<CartDataResponse?>): List<CartDataEntity> {
    return cartData.map {
        CartDataEntity(
            id = it?.id,
            product = it?.product?.toProductCartEntity(),
            qty = it?.qty,
            totalPrice = it?.totalPrice
        )
    }
}

private fun convertImageResponseToEntity(image: List<ImageCartResponse?>): List<ImageCartEntity> {
    return image.map {
        ImageCartEntity(
            name = it?.name,
            publicId = it?.publicId,
            url = it?.url
        )
    }
}