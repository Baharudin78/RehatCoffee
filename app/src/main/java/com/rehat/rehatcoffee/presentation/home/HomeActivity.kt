package com.rehat.rehatcoffee.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.Constants

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
    companion object{
        const val HOME_EXTRA = Constants.HOME_EXTRA
    }
}