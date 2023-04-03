package com.rehat.rehatcoffee.presentation.started

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.databinding.ActivityGetStartedBinding
import com.rehat.rehatcoffee.presentation.home.HomeActivity
import com.rehat.rehatcoffee.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class GetStartedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGetStartedBinding

    @Inject
    lateinit var dataStore : TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            checkIsLoggedIn()
        }
    }

    private suspend fun checkIsLoggedIn(){
        dataStore.userTokenFlow.collect{
            if (it.isNotEmpty()){
                goToHomeActivity()
            }
        }
    }

    private fun goToHomeActivity(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}