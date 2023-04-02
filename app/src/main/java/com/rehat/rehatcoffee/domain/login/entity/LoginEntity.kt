package com.rehat.rehatcoffee.domain.login.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginEntity(
    val userData : UserEntity?,
    val token : String?
) : Parcelable
