package com.rehat.rehatcoffee.core

object Constants {
    const val BASE_URL = "https://rehat-cafe.adaptable.app/api/"
    //Extra
    const val HOME_EXTRA = "HOME_EXTRA"
    const val MY_PREF = "my_preferences"
    const val USER_TOKEN = "user_token"
    const val USER_ROLE_KEY = "user_role"
    const val EXTRA_DATA = "EXTRA_DATA"

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
    const val CART_INDICATOR ="client/cart-indicator"
    const val CREATE_ORDER = "client/order"
    const val GET_ORDER = "client/order"
    const val ORDER_DETAIL = "client/detail-order"
    const val GET_NOTIFICATION = "client/notification"
    const val INDICATOR_NOTIF = "client/notification-indicator"

    const val PRODUCT_ADMIN = "admin/product"
    const val ORDER_ADMIN = "admin/order"

    //Admin
    const val ROLE = "admin"
}