package com.rehat.rehatcoffee.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.TokenDataStore
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.login.remote.dto.LoginRequest
import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import com.rehat.rehatcoffee.databinding.ActivityLoginBinding
import com.rehat.rehatcoffee.domain.login.entity.LoginEntity
import com.rehat.rehatcoffee.presentation.common.extention.isEmail
import com.rehat.rehatcoffee.presentation.common.extention.showGenericAlertDialog
import com.rehat.rehatcoffee.presentation.common.extention.showToast
import com.rehat.rehatcoffee.presentation.home.HomeActivity
import com.rehat.rehatcoffee.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), FirebaseInstanceIdInternal.NewTokenListener {
    private lateinit var binding: ActivityLoginBinding
    private var fcmToken: String? = null
    private val viewModel: LoginViewModel by viewModels()
    private val loginEntity: LoginEntity? = null

    @Inject
    lateinit var dataStore: TokenDataStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        observe()
        goToRegisterActivity()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val email = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validate(email, password)) {
                val loginRequest = fcmToken?.let { it1 -> LoginRequest(email, password, it1) }
                if (loginRequest != null) {
                    viewModel.login(loginRequest)
                }
            }
        }
    }


    private fun validate(email: String, password: String): Boolean {
        resetAllInputError()
        if (!email.isEmail()) {
            setEmailError(getString(R.string.error_email_not_valid))
            return false
        }

        if (password.length < 8) {
            setPasswordError(getString(R.string.error_password_not_valid))
            return false
        }

        return true
    }

    private fun resetAllInputError() {
        setEmailError(null)
        setPasswordError(null)
    }

    private fun setEmailError(e: String?) {
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

    private suspend fun handleStateChange(state: LoginViewState) {
        when (state) {
            is LoginViewState.Init -> Unit
            is LoginViewState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginViewState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginViewState.ShowToast -> showToast(state.message)
            is LoginViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>) {
        showGenericAlertDialog(response.message)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.btnLogin.isEnabled = !isLoading
        binding.btnRegister.isEnabled = !isLoading
        binding.progress.isIndeterminate = isLoading
        if (!isLoading) {
            binding.progress.progress = 0
        }
    }

    private suspend fun handleSuccessLogin(loginEntity: LoginEntity) {
        loginEntity.token?.let {
            dataStore.saveUserToken(it)
        }
        goToMainActivity(loginEntity)
    }

    private val openRegisterActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                goToMainActivity(loginEntity)
            }
        }

    private fun goToRegisterActivity() {
        binding.btnRegister.setOnClickListener {
            openRegisterActivity.launch(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun goToMainActivity(loginEntity: LoginEntity?) {
        startActivity(
            Intent(this@LoginActivity, HomeActivity::class.java)
                .putExtra(HomeActivity.HOME_EXTRA, loginEntity)
        )
        finish()
    }

    override fun onNewToken(token: String?) {
        fcmToken = token
    }
}