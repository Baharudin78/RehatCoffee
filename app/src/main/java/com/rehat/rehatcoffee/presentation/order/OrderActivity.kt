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
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.databinding.ActivityOrderBinding
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.presentation.cart.OrderViewState
import com.rehat.rehatcoffee.presentation.common.extention.*
import com.rehat.rehatcoffee.presentation.home.HomeActivity
import com.rehat.rehatcoffee.presentation.order.adapter.OrderDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderBinding
    private var orderEntity : OrderEntity? = null
    private val viewModel : OrderViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderEntity = intent.parcelable(ORDER_DATA) as OrderEntity?

        getOrderDetail()
        setupRecycleview()
        observe()
    }

    private fun getOrderDetail(){
        if (orderEntity != null){
            orderEntity!!.id?.let {
                viewModel.getOrderDetai(it)
            }
        }
    }

    private fun setupRecycleview(){
        val orderAdapter = OrderDetailAdapter(mutableListOf())
        binding.rvOrder.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@OrderActivity)
        }
    }
    private fun observe(){
        observeState()
        observeOrderDetail()
    }

    private fun observeState(){
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateOrderDetail(state) }
            .launchIn(lifecycleScope)
    }

    private fun observeOrderDetail(){
        viewModel.order.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { product ->
                product?.let { handleProduct(it) }
            }
            .launchIn(lifecycleScope)
    }

    private fun handleProduct(orderEntity: OrderEntity){
        val orders = orderEntity.product
        binding.rvOrder.adapter?.let { order ->
            if (order is OrderDetailAdapter){
                order.updateOrder(orders)
            }
        }
        binding.tvTotal.text = "Total Bayar Rp${orderEntity.totalPrice}"
    }

    private fun handleStateOrderDetail(state: OrderViewState) {
        when (state) {
            is OrderViewState.Init -> Unit
            is OrderViewState.ErrorOrder -> handleErrorOrder(state.rawResponse)
           // is OrderViewState.SuccessOrder -> handleSuccessOrder(state.orderEntity)
            is OrderViewState.ShowToast -> showToast(state.message)
            is OrderViewState.IsLoading -> handleLoading(state.isLoading)
            else -> {}
        }
    }

    private fun handleErrorOrder(response: WrappedResponse<OrderResponse>) {
        showGenericAlertDialog(response.message)
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        goToHome()
        finish()
    }
    private fun goToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    companion object{
        const val ORDER_DATA = "ORDER_DATA"
    }
}