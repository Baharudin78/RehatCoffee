package com.rehat.rehatcoffee.domain.login.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val email: String? = null,
    val fcmToken: String? = null,
    val name: String? = null,
    val password: String? = null,
    val role: String? = null,
) : Parcelable
