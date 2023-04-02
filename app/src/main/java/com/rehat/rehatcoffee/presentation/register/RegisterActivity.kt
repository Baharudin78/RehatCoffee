package com.rehat.rehatcoffee.presentation.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants.MIN_PASSWORD_LENGTH
import com.rehat.rehatcoffee.core.Constants.MIN_USERNAME_LENGTH
import com.rehat.rehatcoffee.core.Constants.ROLE_EMPTY
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterRequest
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import com.rehat.rehatcoffee.databinding.ActivityRegisterBinding
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.domain.register.entity.RegisterEntity
import com.rehat.rehatcoffee.presentation.common.extention.isEmail
import com.rehat.rehatcoffee.presentation.common.extention.showGenericAlertDialog
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.home.HomeActivity
import com.rehat.rehatcoffee.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var fcmToken: String? = null
    private val viewModel: RegisterViewModel by viewModels()
    private val registerEntity: RegisterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveFCMToken()
        register()
        observe()
        goLoginActivity()
    }

    private fun retrieveFCMToken() {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                Log.d(TAG, "FCM Token : $token")
                fcmToken = token
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to retrieve FCM token", exception)
            }
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val role = binding.tvRole.text.toString()
            if (validate(email, username, password, role)) {
                val registerRequest = fcmToken?.let { token ->
                    RegisterRequest(
                        username,
                        email,
                        password,
                        role,
                        token
                    )
                }
                if (registerRequest != null) {
                    viewModel.register(registerRequest)
                }
            }
        }
    }

    private fun validate(email: String, username: String, password: String, role: String): Boolean {
        resetAllInputError()
        if (!email.isEmail()) {
            setEmailError(getString(R.string.error_email_not_valid))
            return false
        }

        if (username.length < MIN_USERNAME_LENGTH) {
            setUsernameError(getString(R.string.error_username))
            return false
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            setPasswordError(getString(R.string.error_password_not_valid))
            return false
        }

        if (role.isEmpty()) {
            showToast(ROLE_EMPTY)
        }

        return true
    }

    private fun resetAllInputError() {
        setEmailError(null)
        setUsernameError(null)
        setPasswordError(null)
    }

    private fun setEmailError(e: String?) {
        binding.usernameInputLayout.error = e
    }

    private fun setUsernameError(e: String?) {
        binding.usernameInputLayout.error = e
    }

    private fun setPasswordError(e: String?) {
        binding.passwordInputLayout.error = e
    }

    private fun observe() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleSuccessRegister() {
        goToLoginActivity()
    }

    private fun handleStateChange(state: RegisterViewState) {
        when (state) {
            is RegisterViewState.Init -> Unit
            is RegisterViewState.ErrorRegister -> handleErrorRegister(state.rawResponse)
            is RegisterViewState.SuccessRegister -> handleSuccessRegister()
            is RegisterViewState.ShowToast -> showToast(state.message)
            is RegisterViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleErrorRegister(response: WrappedResponse<RegisterResponse>) {
        showGenericAlertDialog(response.message)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.btnRegister.isEnabled = !isLoading
        binding.btnRegister.isEnabled = !isLoading
        binding.progress.isIndeterminate = isLoading
        if (!isLoading) {
            binding.progress.progress = 0
        }
    }

    private fun goToLoginActivity() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
        finish()
    }

    private fun goLoginActivity() {
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}