package com.rehat.rehatcoffee.presentation.common.extention

import android.app.AlertDialog
import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.rehat.rehatcoffee.R

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.button_text_ok)){ dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}

fun withDelay(delay: Long = 100, block: () -> Unit) {
    android.os.Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}