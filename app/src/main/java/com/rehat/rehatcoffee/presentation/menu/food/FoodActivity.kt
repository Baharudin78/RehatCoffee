package com.rehat.rehatcoffee.presentation.menu.food

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.databinding.ActivityFoodBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.cart.CartActivity
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showGenericAlertDialog
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.food.adapter.FoodAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private val viewModel: FoodViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleView()
        fetchMenuFood()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            icBack.setOnClickListener {
                finish()
            }
            btnOrder.setOnClickListener {
                startActivity(Intent(this@FoodActivity, CartActivity::class.java))
            }
            btnToCart.setOnClickListener {
                startActivity(Intent(this@FoodActivity, CartActivity::class.java))
            }
        }

    }

    private fun setupRecycleView() {
        val foodAdapter = FoodAdapter(mutableListOf())
        foodAdapter.setItemClicktoCart(object : FoodAdapter.OnItemClickToCart {
            override fun onClickToCart(menuEntity: MenuEntity) {
                Toast.makeText(this@FoodActivity, "Ditambahkan",Toast.LENGTH_SHORT).show()
                menuEntity.id?.let {
                    viewModel.createCart(it)
                    viewModel.getCartIndicator()
                }
            }
        })

        binding.rvFood.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@FoodActivity)
        }
    }

    private fun fetchMenuFood() {
        viewModel.fetchMenuFood()
        viewModel.getCartIndicator()
    }

    private fun initObserver() {
        observeFood()
        observeState()
        observeCart()
        observeCartCountState()
        observeCartCount()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeCartCountState() {
        viewModel.cartIndicatorState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleCartCountState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeCartCount() {
        viewModel.cartCount
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { cartCount ->
                handleCartCount(cartCount)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeFood() {
        viewModel.foods
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { foods ->
                handleFoods(foods)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeCart() {
        viewModel.cart
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { cartState ->
                handleStateCart(cartState)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleFoods(foods: List<MenuEntity>) {
        binding.rvFood.adapter?.let { food ->
            if (food is FoodAdapter) {
                food.updateListFood(foods)
            }
        }
    }

    private fun handleCartCount(cartCount: CartIndicatorEntity?) {
        val counter = cartCount?.totalCart
        if (counter != null) {
            if (counter >= 1) {
                binding.tvCartCount.visible()
                binding.btnOrder.visible()
            }
        }
        binding.tvCartCount.text = cartCount?.totalCart.toString()
    }

    private fun handleState(state: GetMenuFoodViewState) {
        when (state) {
            is GetMenuFoodViewState.IsLoading -> handleLoading(state.isLoading)
            is GetMenuFoodViewState.ShowToast -> this.showToast(state.message)
            is GetMenuFoodViewState.Init -> Unit
            else -> {}
        }
    }

    private fun handleCartCountState(state: CartIndicatorViewState) {
        when (state) {
            is CartIndicatorViewState.SuccessCartCount -> handleCartCount(state.cartIndicatorEntity)
            is CartIndicatorViewState.ShowToast -> this.showToast(state.message)
            is CartIndicatorViewState.Init -> Unit
            else -> {}
        }
    }

    private fun handleStateCart(state: CartViewState) {
        when (state) {
            is CartViewState.Init -> Unit
            is CartViewState.Error -> handleErrorCart(state.rawResponse)
            is CartViewState.SuccessCreateCart -> handleSuccessCreateCart()
            is CartViewState.ShowToast -> showToast(state.message)
            else -> {}
        }
    }

    private fun handleErrorCart(response: WrappedResponse<CartDataResponse>) {
        showGenericAlertDialog(response.message)
    }

    private fun handleSuccessCreateCart() {
        binding.btnOrder.visible()
        binding.progressBar.gone()
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }
}