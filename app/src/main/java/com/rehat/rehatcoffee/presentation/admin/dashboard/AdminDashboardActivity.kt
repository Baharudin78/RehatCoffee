package com.rehat.rehatcoffee.presentation.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.databinding.ActivityAdminDashboardBinding
import com.rehat.rehatcoffee.presentation.admin.menu.MenuActivity
import com.rehat.rehatcoffee.presentation.admin.orderan.ListOrderActivity
import com.rehat.rehatcoffee.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminDashboardBinding
    @Inject
    lateinit var dataStore : TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnMenu.setOnClickListener {
                startActivity(Intent(this@AdminDashboardActivity, MenuActivity::class.java))
            }
            btnListOrder.setOnClickListener {
                startActivity(Intent(this@AdminDashboardActivity, ListOrderActivity::class.java))
            }
            btnLogout.setOnClickListener {
                lifecycleScope.launch {
                    logOut()
                }
            }
        }
    }

    private suspend fun logOut(){
        dataStore.clearUserToken()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }
}