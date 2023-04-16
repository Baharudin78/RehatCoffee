package com.rehat.rehatcoffee.presentation.admin.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rehat.rehatcoffee.databinding.ActivityMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

    }

    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            ivFood.setOnClickListener {
                startActivity(Intent(this@MenuActivity, AdminFoodActivity::class.java))
            }
            ivDrink.setOnClickListener {
                startActivity(Intent(this@MenuActivity, AdminDrinkActivity::class.java))

            }

        }

    }
}