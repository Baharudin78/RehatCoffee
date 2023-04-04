package com.rehat.rehatcoffee.presentation.menu.drink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityDrinkBinding
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.drink.adapter.DrinkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@AndroidEntryPoint
class DrinkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrinkBinding
    private val viewModel: DrinkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        fetchMenuDrink()
        initObserver()
    }

    private fun setupRecycleview() {
        val drinkAdapter = DrinkAdapter(mutableListOf())
        drinkAdapter.setItemClickListener(object : DrinkAdapter.OnItemClick {
            override fun onClick(menuEntity: MenuEntity) {
                showToast("Click")
            }
        })
        binding.rvDrink.apply {
            adapter = drinkAdapter
            layoutManager = LinearLayoutManager(this@DrinkActivity)
        }
    }

    private fun fetchMenuDrink() {
        viewModel.fetchMenuDrink()
    }
    private fun initObserver(){
        observeState()
        observeDrink()
    }

    private fun observeState(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeDrink(){
        viewModel.drinks
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { drinks ->
                handleDrink(drinks)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleDrink(drink : List<MenuEntity>){
        binding.rvDrink.adapter?.let { drinks ->
            if(drinks is DrinkAdapter){
                drinks.updateListDrink(drink)
            }
        }
    }
    private fun handleState(state : GetMenuDrinkViewState){
        when(state){
            is GetMenuDrinkViewState.IsLoading -> handleLoading(state.isLoading)
            is GetMenuDrinkViewState.ShowToast -> this.showToast(state.message)
            is GetMenuDrinkViewState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visible()
        }else{
            binding.progressBar.gone()
        }
    }
}