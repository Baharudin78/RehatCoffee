package com.rehat.rehatcoffee.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.databinding.ActivityHomeBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.presentation.cart.CartActivity
import com.rehat.rehatcoffee.presentation.cart.CartViewModel
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.login.LoginActivity
import com.rehat.rehatcoffee.presentation.menu.drink.DrinkActivity
import com.rehat.rehatcoffee.presentation.menu.food.CartIndicatorViewState
import com.rehat.rehatcoffee.presentation.menu.food.FoodActivity
import com.rehat.rehatcoffee.presentation.notification.NotificationActivity
import com.rehat.rehatcoffee.presentation.order.OrderListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel : HomeViewModel by viewModels()

    @Inject
    lateinit var dataStore : TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchFromApi()
        initObserver()

        binding.apply {
            ivDrink.setOnClickListener {
                startActivity(Intent(this@HomeActivity, DrinkActivity::class.java))
            }
            ivFood.setOnClickListener {
                startActivity(Intent(this@HomeActivity, FoodActivity::class.java))
            }
            btnToCart.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CartActivity::class.java))
            }
            btnToNotif.setOnClickListener {
                startActivity(Intent(this@HomeActivity, NotificationActivity::class.java))
            }
            btnToList.setOnClickListener {
                startActivity(Intent(this@HomeActivity, OrderListActivity::class.java))
            }
        }
    }

    private fun fetchFromApi(){
        viewModel.getCartIndicator()
    }

    private fun initObserver(){
        observeCartCountState()
        observeCartCount()
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
    private fun handleCartCountState(state: CartIndicatorViewState) {
        when (state) {
            is CartIndicatorViewState.SuccessCartCount -> handleCartCount(state.cartIndicatorEntity)
            is CartIndicatorViewState.ShowToast -> this.showToast(state.message)
            is CartIndicatorViewState.Init -> Unit
            else -> {}
        }
    }

    private fun handleCartCount(cartCount: CartIndicatorEntity?) {
        val counter = cartCount?.totalCart
        if (counter != null) {
            if (counter >= 1) {
                binding.tvCartCount.visible()
            }
        }
        binding.tvCartCount.text = cartCount?.totalCart.toString()
    }
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            checkIsLoggedIn()
        }
    }
    private suspend fun checkIsLoggedIn(){
        dataStore.userTokenFlow.collect{
            if (it.isEmpty()){
                goToLoginActivity()
            }
        }
    }

    private fun goToLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    companion object{
        const val HOME_EXTRA = Constants.HOME_EXTRA
    }
}