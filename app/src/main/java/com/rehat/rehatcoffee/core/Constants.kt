package com.rehat.rehatcoffee.core

object Constants {
    const val BASE_URL = "https://app.arsip-surat-elektronik.my.id/api/"
    //Extra
    const val HOME_EXTRA = "HOME_EXTRA"
    const val MY_PREF = "my_preferences"
    const val USER_TOKEN = "user_token"

    //Additional
    const val ROLE_EMPTY = "Role Jangan Kosong"
    const val MIN_USERNAME_LENGTH = 8
    const val MIN_PASSWORD_LENGTH = 8

    //Endpoints
    const val LOGIN_URL = "client/login"
    const val REGISTER_URL = "client/register"
    const val PRODUCTS_DRINK = "client/product?kinds=drink"
    const val PRODUCTS_FOOD = "client/product?kinds=food"
    const val CART_URL = "client/cart"
}