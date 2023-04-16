package com.rehat.rehatcoffee.presentation.admin.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityAdminDrinkBinding
import com.rehat.rehatcoffee.databinding.ActivityDrinkBinding
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.presentation.admin.create.CreateMenuActivity
import com.rehat.rehatcoffee.presentation.admin.create.EditProductActivity
import com.rehat.rehatcoffee.presentation.admin.menu.adapter.ProductAdapter
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AdminDrinkActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminDrinkBinding
    private val viewModel : MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        observer()
        fetchFood()
        initListener()
    }

    private fun initListener(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvAddFood.setOnClickListener {
                val intent = Intent(this@AdminDrinkActivity, CreateMenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupRecycleview(){
        val drinkAdapter = ProductAdapter(mutableListOf())
        drinkAdapter.setItemClicktoDelete(object : ProductAdapter.OnItemClickToDelete{
            override fun onClickToDelete(productEntity: ProductEntity) {
                productEntity.description?.let {
                    viewModel.deleteProduct(it)
                }
            }
        })
        drinkAdapter.setItemClicktoUpdate(object : ProductAdapter.OnItemClickToUpdate{
            override fun onClickToUpdate(productEntity: ProductEntity) {
                val intent = Intent(this@AdminDrinkActivity, EditProductActivity::class.java)
                    .putExtra(EditProductActivity.EDIT_PRODUCT, productEntity)
                startActivity(intent)
            }
        })
        binding.rvDrink.apply {
            adapter = drinkAdapter
            layoutManager = LinearLayoutManager(this@AdminDrinkActivity)
        }
    }

    private fun fetchFood(){
        viewModel.fetchMenuDrink("drink")
    }

    private fun observer(){
        observeState()
        observeFood()
        observeDelete()

    }

    private fun observeState(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeFood() {
        viewModel.menus
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { foods ->
                handleFoods(foods)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeDelete() {
        viewModel.deleteCartResult
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                it?.message?.let { it1 -> showToast(it1) }
                viewModel.fetchMenuDrink("drink")
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: GetMenuViewState) {
        when (state) {
            is GetMenuViewState.IsLoading -> handleLoading(state.isLoading)
            is GetMenuViewState.ShowToast -> this.showToast(state.message)
            is GetMenuViewState.Init -> Unit
            else -> {}
        }
    }

    private fun handleFoods(foods: List<ProductEntity>) {
        binding.rvDrink.adapter?.let { food ->
            if (food is ProductAdapter) {
                food.updateListFood(foods)
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