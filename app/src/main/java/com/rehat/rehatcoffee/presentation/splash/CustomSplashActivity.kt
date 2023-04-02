package com.rehat.rehatcoffee.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.databinding.ActivityCustomSplashBinding
import com.rehat.rehatcoffee.presentation.home.HomeActivity
import com.rehat.rehatcoffee.presentation.started.GetStartedActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CustomSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomSplashBinding

    @Inject
    lateinit var dataStore: TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentHome = Intent(this, GetStartedActivity::class.java)
            startActivity(intentHome)
            finish()
        }, 1000)
    }

    override fun onStart() {
        super.onStart()
        runBlocking {
            checkIsLoggedIn()
        }
    }

    private suspend fun checkIsLoggedIn(){
        dataStore.userTokenFlow.collect{
            if (it.isEmpty()){
                goToLoginActivity()
            }else{
                goToHomeActivity()
            }
        }
    }
    private fun goToLoginActivity(){
        startActivity(Intent(this, GetStartedActivity::class.java))
        finish()
    }
    private fun goToHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}