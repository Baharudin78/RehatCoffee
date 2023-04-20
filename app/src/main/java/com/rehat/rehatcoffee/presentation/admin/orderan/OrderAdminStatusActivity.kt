package com.rehat.rehatcoffee.presentation.admin.orderan

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.core.Constants
import com.rehat.rehatcoffee.databinding.ActivityOrderAdminStatusBinding
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.presentation.common.extention.parcelable
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OrderAdminStatusActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderAdminStatusBinding
    private val viewModel : OrderAdminViewModel by viewModels()
    private var adminOrder: AdminOrderEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderAdminStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        binding.close.setOnClickListener {
            finish()
        }
        adminOrder = intent.parcelable(Constants.EXTRA_DATA) as AdminOrderEntity?

        observeState()
        initListener()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun initListener(){
        binding.btnKonfirm.setOnClickListener {
            adminOrder?.id?.let { id ->
                viewModel.updateOrderStatus(id,  true)
            }
        }

        binding.btnNo.setOnClickListener {
            finish()
        }
    }

    private fun handleState(state: AdminOrderViewState) {
        when (state) {
            is AdminOrderViewState.SuccessUpdateOrder -> successUpdate()
            is AdminOrderViewState.Init -> Unit
            is AdminOrderViewState.ShowToast -> this.showToast(state.message)
            else -> {}
        }
    }

    private fun successUpdate() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}