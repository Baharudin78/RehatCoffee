package com.rehat.rehatcoffee.presentation.admin.orderan

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants
import com.rehat.rehatcoffee.databinding.ActivityListOrderBinding
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.admin.orderan.adapter.AdminOrderAdapter
import com.rehat.rehatcoffee.presentation.cart.CartViewState
import com.rehat.rehatcoffee.presentation.cart.EditCartActivity
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.drink.adapter.DrinkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListOrderBinding
    private val viewModel : OrderAdminViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        setupRecycleview()
        initObserver()
        fetchOrder()
    }

    private fun initListener(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
    private fun setupRecycleview(){
        val adapterOrder = AdminOrderAdapter(mutableListOf())
        adapterOrder.setItemClicktoDone(object : AdminOrderAdapter.OnItemClickToDone{
            override fun onClickToDone(adminOrder: AdminOrderEntity) {
                val intent = Intent(this@ListOrderActivity, OrderAdminConfirmActivity::class.java)
                    .putExtra(Constants.EXTRA_DATA, adminOrder)
                updateResut.launch(intent)
            }
        })
    }

    private fun fetchOrder(){
        viewModel.fetchOrder(true)
    }

    private fun initObserver(){
        observeState()
        observeOrder()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeOrder() {
        viewModel.orders
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { cart ->
                handleOrder(cart)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleOrder(drink: List<AdminOrderEntity>) {
        binding.rvOrder.adapter?.let { drinks ->
            if (drinks is AdminOrderAdapter) {
                drinks.updateListOrder(drink)
            }
        }
    }

    private fun handleState(state: AdminOrderViewState) {
        when (state) {
            is AdminOrderViewState.IsLoading -> handleLoading(state.isLoading)
            is AdminOrderViewState.ShowToast -> this.showToast(state.message)
            is AdminOrderViewState.Init -> Unit
            else -> {}
        }
    }

    private val updateResut =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.fetchOrder(true)
            }
        }


    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }



}