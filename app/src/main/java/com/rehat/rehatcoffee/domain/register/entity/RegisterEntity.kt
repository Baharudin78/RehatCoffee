package com.rehat.rehatcoffee.domain.register.entity

import android.os.Parcelable
import com.rehat.rehatcoffee.domain.login.entity.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterEntity(
    val userData : UserRegisterEntity?,
    val token : String?
) : Parcelable
