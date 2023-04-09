package com.rehat.rehatcoffee.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityOrderListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderListBinding
    private val viewModel : OrderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}