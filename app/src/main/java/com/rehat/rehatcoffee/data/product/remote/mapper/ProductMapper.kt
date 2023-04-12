package com.rehat.rehatcoffee.data.product.remote.mapper

import com.rehat.rehatcoffee.data.product.remote.dto.ImageProductResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.domain.product.entity.ImageProductEntity
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity

fun ProductResponse.toProductEntity(): ProductEntity{
    return ProductEntity(
        description,
        id,
        convertImageResponseToEntity(images),
        kinds, price, productName
    )
}

fun ImageProductResponse.toImageMenuEntity(): ImageProductEntity {
    return ImageProductEntity(
        name, publicId, url
    )
}

fun convertImageResponseToEntity(image : List<ImageProductResponse?>): List<ImageProductEntity>{
    return image.map {
        ImageProductEntity(
            name = it?.name,
            publicId = it?.publicId,
            url = it?.url
        )
    }
}