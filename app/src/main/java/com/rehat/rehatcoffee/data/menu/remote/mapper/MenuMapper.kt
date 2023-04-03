package com.rehat.rehatcoffee.data.menu.remote.mapper

import com.rehat.rehatcoffee.data.menu.remote.dto.ImageMenuResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.domain.menu.entity.ImageMenuEntity
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity

fun MenuResponse.toMenuEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        description = description,
        imagesEntity = images
    )

}

fun ImageMenuResponse.toImageMenuEntity(): ImageMenuEntity {
    return ImageMenuEntity(
        name, publicId, url
    )
}

