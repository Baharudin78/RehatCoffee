package com.rehat.rehatcoffee.core

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("MyFirebaseMessagingServ", "Refreshed token: $token")
    }
}