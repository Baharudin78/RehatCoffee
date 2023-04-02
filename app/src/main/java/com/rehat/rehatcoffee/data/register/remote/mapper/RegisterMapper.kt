package com.rehat.rehatcoffee.data.register.remote.mapper

import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.data.register.remote.dto.UserRegisterResponse
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.domain.register.entity.UserRegisterEntity

fun RegisterResponse.toRegisterEntity() : RegisterEntity {
    return RegisterEntity(
        userData = register.toUserRegisterEntity(),
        token = token
    )
}

fun UserRegisterResponse.toUserRegisterEntity() : UserRegisterEntity {
    return UserRegisterEntity(email, fcmToken, name, password, role)
}