package com.rehat.rehatcoffee.presentation.menu.drink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.databinding.ActivityDrinkBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.cart.CartActivity
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showGenericAlertDialog
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.drink.adapter.DrinkAdapter
import com.rehat.rehatcoffee.presentation.menu.food.CartIndicatorViewState
import com.rehat.rehatcoffee.presentation.menu.food.CartViewState
import com.rehat.rehatcoffee.presentation.menu.food.FoodViewModel
import com.rehat.rehatcoffee.presentation.menu.food.GetMenuFoodViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DrinkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrinkBinding
    private val viewModel: FoodViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        fetchMenuDrink()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            btnOrder.setOnClickListener {
                startActivity(Intent(this@DrinkActivity, CartActivity::class.java))
            }
            btnToCart.setOnClickListener {
                startActivity(Intent(this@DrinkActivity, CartActivity::class.java))
            }
        }
    }

    private fun setupRecycleview() {
        val drinkAdapter = DrinkAdapter(mutableListOf())
        drinkAdapter.setItemClicktoCart(object : DrinkAdapter.OnItemClickToCart {
            override fun onClickToCart(menuEntity: MenuEntity) {
                Toast.makeText(this@DrinkActivity, "Ditambahkan", Toast.LENGTH_SHORT).show()
                menuEntity.id?.let {
                    viewModel.createCart(it)
                    viewModel.getCartIndicator()
                }
            }
        })

        binding.rvDrink.apply {
            adapter = drinkAdapter
            layoutManager = LinearLayoutManager(this@DrinkActivity)
        }
    }

    private fun fetchMenuDrink() {
        viewModel.fetchMenuDrink()
        viewModel.getCartIndicator()
    }

    private fun initObserver() {
        observeState()
        observeDrink()
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

    private fun observeDrink() {
        viewModel.foods
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { drinks ->
                handleDrink(drinks)
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

    private fun handleDrink(drink: List<MenuEntity>) {
        binding.rvDrink.adapter?.let { drinks ->
            if (drinks is DrinkAdapter) {
                drinks.updateListDrink(drink)
            }
        }
    }

    private fun handleStateCart(state: CartViewState) {
        when (state) {
            is CartViewState.Init -> Unit
            is CartViewState.Error -> handleErrorCart(state.rawResponse)
            is CartViewState.SuccessCreateCart -> handleSuccessCreateCart()
            is CartViewState.SuccessDeleteCart -> handleSuccessDeleteCart()
            is CartViewState.ShowToast -> showToast(state.message)
            else -> {}
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

    private fun handleCartCountState(state: CartIndicatorViewState) {
        when (state) {
            is CartIndicatorViewState.ShowToast -> this.showToast(state.message)
            is CartIndicatorViewState.Init -> Unit
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

    private fun handleSuccessDeleteCart() {
        showToast("Success Delete Cart")
    }

    private fun handleState(state: GetMenuFoodViewState) {
        when (state) {
            is GetMenuFoodViewState.IsLoading -> handleLoading(state.isLoading)
            is GetMenuFoodViewState.ShowToast -> this.showToast(state.message)
            is GetMenuFoodViewState.Init -> Unit
            else -> {}
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