package com.rehat.rehatcoffee.data.menu.remote.mapper

import com.rehat.rehatcoffee.data.menu.remote.dto.ImageMenuResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.domain.menu.entity.ImageMenuEntity
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity

fun MenuResponse.toMenuEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        description = description,
        imagesEntity = convertImageResponseToEntity(images)
    )

}

fun ImageMenuResponse.toImageMenuEntity(): ImageMenuEntity {
    return ImageMenuEntity(
        name, publicId, url
    )
}

private fun convertImageResponseToEntity(image : List<ImageMenuResponse?>): List<ImageMenuEntity>{
    return image.map {
        ImageMenuEntity(
            name = it?.name,
            publicId = it?.publicId,
            url = it?.url
        )
    }
}

