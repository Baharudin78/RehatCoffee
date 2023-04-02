package com.rehat.rehatcoffee.data.login.remote.mapper

import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import com.rehat.rehatcoffee.data.login.remote.dto.UserResponse
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.domain.login.entity.UserEntity

fun LoginResponse.toLoginEntity(): LoginEntity {
    return LoginEntity(
        userData = user?.toUserEntity(),
        token = token
    )
}

private fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        email, fcmToken, name, password, role
    )
}