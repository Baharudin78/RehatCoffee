package com.rehat.rehatcoffee.presentation.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityAdminDashboardBinding
import com.rehat.rehatcoffee.presentation.admin.menu.MenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnMenu.setOnClickListener {
                startActivity(Intent(this@AdminDashboardActivity, MenuActivity::class.java))
            }
            btnListOrder.setOnClickListener {

            }
        }
    }
}