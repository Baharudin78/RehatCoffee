package com.rehat.rehatcoffee.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants
import com.rehat.rehatcoffee.databinding.ActivityHomeBinding
import com.rehat.rehatcoffee.presentation.menu.drink.DrinkActivity
import com.rehat.rehatcoffee.presentation.menu.food.FoodActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
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
        }
    }
    companion object{
        const val HOME_EXTRA = Constants.HOME_EXTRA
    }
}