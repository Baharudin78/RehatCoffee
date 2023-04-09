package com.rehat.rehatcoffee.presentation.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityOrderListBinding
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.presentation.cart.OrderViewState
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.food.GetMenuFoodViewState
import com.rehat.rehatcoffee.presentation.order.adapter.OrderListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderListBinding
    private val viewModel : OrderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        fetchOrder()
        initObserver()
        initListener()
    }

    private fun initListener(){
        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
        }
    }
    private fun setupRecycleview(){
        val orderAdapter = OrderListAdapter(mutableListOf())
        orderAdapter.setItemClickListener(object : OrderListAdapter.OnItemClickListener{
            override fun onClickListener(orderEntity: OrderEntity) {
                val intent = Intent(this@OrderListActivity, OrderActivity::class.java)
                    .putExtra(OrderActivity.ORDER_DATA, orderEntity)
                startActivity(intent)
            }
        })
        binding.rvOrder.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@OrderListActivity)
        }
    }
    private fun fetchOrder(){
        viewModel.fetchOrder()
    }

    private fun initObserver(){
        observeOrder()
        observeState()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: OrderViewState) {
        when (state) {
            is OrderViewState.IsLoading -> handleLoading(state.isLoading)
            is OrderViewState.ShowToast -> this.showToast(state.message)
            is OrderViewState.Init -> Unit
            else -> {}
        }
    }
    private fun observeOrder(){
        viewModel.listOrder
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { orderEntities ->
                handleListOrder(orderEntities)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleListOrder(order : List<OrderEntity>){
        binding.rvOrder.adapter?.let { orders ->
            if (orders is OrderListAdapter){
                orders.updateList(order)
            }
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