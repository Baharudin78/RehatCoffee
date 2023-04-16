package com.rehat.rehatcoffee.data.adminorder.remote.mapper

import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminProductItemOrderResponse
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminProductOrderResponse
import com.rehat.rehatcoffee.data.adminorder.remote.dto.ImageProductResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminProductItemOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminProductOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.entity.ImageEntity

fun AdminOrderResponse.toOrderAdminEntity(): AdminOrderEntity{
    return AdminOrderEntity(
        id,
        orderStatus,
        payStatus,
        convertResponseToProductlistOrderEntity(product),
        totalPrice
    )
}

private fun convertResponseToProductlistOrderEntity(product: List<AdminProductOrderResponse?>): List<AdminProductOrderEntity> {
    return product.map {
        AdminProductOrderEntity(
            id = it?.id,
            product = it?.product?.toProductOrderDetailEntity(),
            qty = it?.qty,
            totalPrice = it?.totalPrice
        )
    }
}

private fun AdminProductItemOrderResponse.toProductOrderDetailEntity(): AdminProductItemOrderEntity {
    return AdminProductItemOrderEntity(
        id,
        convertImageResponseToEntity(images),
        kinds, price, productName
    )
}


private fun convertImageResponseToEntity(image: List<ImageProductResponse?>): List<ImageEntity> {
    return image.map {
        ImageEntity(
            name = it?.name,
            publicId = it?.publicId,
            url = it?.url
        )
    }
}