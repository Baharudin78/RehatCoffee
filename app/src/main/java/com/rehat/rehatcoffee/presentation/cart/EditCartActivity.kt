package com.rehat.rehatcoffee.presentation.cart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants.EXTRA_DATA
import com.rehat.rehatcoffee.databinding.ActivityEditCartBinding
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.presentation.common.extention.parcelable
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EditCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCartBinding
    private val viewModel: CartViewModel by viewModels()
    private var cartEntity: CartDataEntity? = null
    private var counterQty: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFinishOnTouchOutside(false)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        binding.close.setOnClickListener {
            finish()
        }
        cartEntity = intent.parcelable(EXTRA_DATA) as CartDataEntity?
        counterQty = cartEntity?.qty!!
        observeState()
        getData()
        updateData()
        updateCounter()

        binding.btnPlus.setOnClickListener {
            counterQty = counterQty!! + 1
            updateCounter()
        }

        binding.btnMinus.setOnClickListener {
            if (counterQty > 1) {
                counterQty--
                updateCounter()
            }
        }
    }

    private fun updateData() {
        binding.btnSave.setOnClickListener {
            cartEntity?.id?.let { id ->
                viewModel.updateCart(id, counterQty)
            }
        }
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: CartViewState) {
        when (state) {
            is CartViewState.SuccessUpdateCart -> successUpdate()
            is CartViewState.Init -> Unit
            is CartViewState.ShowToast -> this.showToast(state.message)
            else -> {}
        }
    }

    private fun successUpdate() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getData() {
        cartEntity?.product?.images?.map {
            Glide.with(binding.root.context)
                .load(it?.url)
                .placeholder(R.drawable.vegetable)
                .load(binding.pict)
        }
        binding.apply {
            name.text = cartEntity?.product?.productName
            price.text = cartEntity?.totalPrice?.toString()
            counter.text = cartEntity?.qty.toString()
        }
    }

    private fun updateCounter() {
        binding.apply {
            counter.text = counterQty.toString()
            btnMinus.isEnabled = counterQty > 1
        }
    }

}