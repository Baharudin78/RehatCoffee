package com.rehat.rehatcoffee.data.order.remote.mapper

import com.rehat.rehatcoffee.data.order.remote.dto.ImageProductOrderResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.data.order.remote.dto.ProductOrderDetailResponse
import com.rehat.rehatcoffee.data.order.remote.dto.ProductOrderListResponse
import com.rehat.rehatcoffee.domain.order.entity.ImageProductOrderEntity
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.entity.ProductOrderDetailEntity
import com.rehat.rehatcoffee.domain.order.entity.ProductOrderListEntity

fun OrderResponse.toOrderEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        orderStatus = orderStatus,
        product = convertProductOrderListResponseToProductlistOrderEntity(product),
        totalPrice = totalPrice
    )
}

private fun convertProductOrderListResponseToProductlistOrderEntity(product: List<ProductOrderListResponse?>): List<ProductOrderListEntity> {
    return product.map {
        ProductOrderListEntity(
            id = it?.id,
            product = it?.product?.toProductOrderDetailEntity(),
            qty = it?.qty,
            totalPrice = it?.totalPrice
        )
    }
}

private fun ProductOrderDetailResponse.toProductOrderDetailEntity(): ProductOrderDetailEntity {
    return ProductOrderDetailEntity(
        id,
        convertImageResponseToEntity(images),
        kinds, price, productName
    )
}


private fun convertImageResponseToEntity(image: List<ImageProductOrderResponse?>): List<ImageProductOrderEntity> {
    return image.map {
        ImageProductOrderEntity(
            name = it?.name,
            publicId = it?.publicId,
            url = it?.url
        )
    }
}

