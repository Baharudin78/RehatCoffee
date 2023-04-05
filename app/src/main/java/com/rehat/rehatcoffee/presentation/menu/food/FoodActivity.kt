package com.rehat.rehatcoffee.presentation.menu.food

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.databinding.ActivityFoodBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.common.extention.gone
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

    private fun initListener(){
        binding.icBack.setOnClickListener {
            finish()
        }
    }
    private fun setupRecycleView() {
        val foodAdapter = FoodAdapter(mutableListOf())
        foodAdapter.setItemClicktoCart(object : FoodAdapter.OnItemClickToCart {
            override fun onClickToCart(menuEntity: MenuEntity) {
                showToast("Click")
            }
        })

        foodAdapter.setItemClickUpdateCart(object  : FoodAdapter.OnItemClickTUpdateCart{
            override fun onClickUpdateCart(menu: MenuEntity) {
                showToast("click")
            }
        })

        foodAdapter.setItemClickDeleteCart(object : FoodAdapter.OnItemClickDeleteCart{
            override fun onClickDeleteCart(menu: MenuEntity) {
                showToast("Click")
            }
        })
        binding.rvFood.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(this@FoodActivity)
        }
    }

    private fun fetchMenuFood() {
        viewModel.fetchMenuFood()
    }

    private fun initObserver() {
        observeFood()
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

    private fun observeFood() {
        viewModel.foods
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { foods ->
                handleFoods(foods)
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

    private fun handleState(state: GetMenuFoodViewState) {
        when (state) {
            is GetMenuFoodViewState.IsLoading -> handleLoading(state.isLoading)
            is GetMenuFoodViewState.ShowToast -> this.showToast(state.message)
            is GetMenuFoodViewState.Init -> Unit
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