package com.ozge.tunestore.common

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showSnackbar(msg: String) {
    Snackbar.make(this, msg, 1500).show()
}

fun EditText.isValidEmail():Boolean{
    val email = this.text?.toString()?.trim() ?: ""
    val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    if (!isValid){
        val parentLayout = this.rootView
        Snackbar.make(parentLayout, "E-mail adresi geçersiz!",Snackbar.LENGTH_SHORT).show()

    }

    return isValid
}

fun EditText.isPasswordValid(): Boolean {
    val password = this.text?.toString() ?: ""
    val isValid = password.length >= 6

    if (!isValid) {
        Toast.makeText(context, "Şifre en az 6 karakter olmalıdır.", Toast.LENGTH_SHORT).show()
    }

    return isValid
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

fun TextInputEditText.isNullorEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (text.toString().trim().isNotEmpty()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun AutoCompleteTextView.checkMonthYear(value: Int, errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (value != 0) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun TextInputEditText.isCreditCardNumberValid(errorMessage: String): Boolean {
    val text = this.text?.toString() ?: ""
    if (text.length != 16 || !text.isNumeric()) {
        this.error = errorMessage
        return false
    }
    return true
}

fun String.isNumeric(): Boolean {
    return this.matches(Regex("[0-9]+"))
}