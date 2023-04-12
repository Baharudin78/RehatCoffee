package com.rehat.rehatcoffee.presentation.admin.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rehat.rehatcoffee.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
    }
}