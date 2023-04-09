package com.rehat.rehatcoffee.presentation.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.databinding.ActivityNotificationBinding
import com.rehat.rehatcoffee.presentation.common.extention.gone
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.common.extention.visible
import com.rehat.rehatcoffee.presentation.menu.food.GetMenuFoodViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotificationBinding
    private val viewModel : NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun fetchNotif(){
        viewModel.fetchNotif()
    }

    private fun initObserver(){
        observeState()
    }

    private fun observeState(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleStateNotif(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleStateNotif(state: NotifViewState) {
        when (state) {
            is NotifViewState.IsLoading -> handleLoading(state.isLoading)
            is NotifViewState.ShowToast -> this.showToast(state.message)
            is NotifViewState.Init -> Unit
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