package com.rehat.rehatcoffee.presentation.cart

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.core.Constants.EXTRA_DATA
import com.rehat.rehatcoffee.databinding.ActivityCartBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.entity.GetCartEntity
import com.rehat.rehatcoffee.presentation.cart.adapter.CartAdapter
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        fetchCart()
        initObserver()
        initListener()
    }

    private fun initListener(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupRecycleview() {
        val cartAdapter = CartAdapter(mutableListOf())
        cartAdapter.setItemClickUpdateCart(object : CartAdapter.OnItemClickToUpdateCart {
            override fun onClickUpdateCart(cart: CartDataEntity) {
                val intent = Intent(this@CartActivity, EditCartActivity::class.java)
                    .putExtra(EXTRA_DATA, cart)
                updateResut.launch(intent)
            }
        })

        cartAdapter.setItemClickDeleteCart(object : CartAdapter.OnItemClickDeleteCart {
            override fun onClickDeleteCart(cart: CartDataEntity) {
                cart.id?.let { viewModel.deleteCart(it) }
            }
        })

        binding.rvCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun fetchCart() {
        viewModel.getCart()
    }

    private fun initObserver() {
        observeState()
        observeCart()
        observeDelete()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeCart() {
        viewModel.cart
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { cart ->
                handleCart(cart)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeDelete() {
        viewModel.deleteCartResult
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                it?.message?.let { it1 -> showToast(it1) }
                viewModel.getCart()
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: CartViewState) {
        when (state) {
            is CartViewState.IsLoading -> handleLoading(state.isLoading)
            is CartViewState.ShowToast -> this.showToast(state.message)
            is CartViewState.Init -> Unit
            else -> {}
        }
    }

    private fun handleCart(cart: GetCartEntity?) {
        val cartData = cart?.cartData ?: emptyList()
        binding.rvCart.adapter?.let { carts ->
            if (carts is CartAdapter) {
                val castedCartData = cartData as? List<CartDataEntity>
                castedCartData?.let {
                    carts.updateCart(it)
                }
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

    private val updateResut =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.getCart()
            }
        }

}