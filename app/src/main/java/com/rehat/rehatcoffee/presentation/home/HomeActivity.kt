package com.rehat.rehatcoffee.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.databinding.ActivityHomeBinding
import com.rehat.rehatcoffee.presentation.cart.CartActivity
import com.rehat.rehatcoffee.presentation.cart.CartViewModel
import com.rehat.rehatcoffee.presentation.login.LoginActivity
import com.rehat.rehatcoffee.presentation.menu.drink.DrinkActivity
import com.rehat.rehatcoffee.presentation.menu.food.FoodActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel : CartViewModel by viewModels()

    @Inject
    lateinit var dataStore : TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivDrink.setOnClickListener {
                startActivity(Intent(this@HomeActivity, DrinkActivity::class.java))
            }
            ivFood.setOnClickListener {
                startActivity(Intent(this@HomeActivity, FoodActivity::class.java))
            }
            btnToCart.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CartActivity::class.java))
            }
            btnToNotif.setOnClickListener {
              //  startActivity(Intent(this@HomeActivity, Noti::class.java))
            }
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
            if (it.isEmpty()){
                goToLoginActivity()
            }
        }
    }

    private fun goToLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    companion object{
        const val HOME_EXTRA = Constants.HOME_EXTRA
    }
}