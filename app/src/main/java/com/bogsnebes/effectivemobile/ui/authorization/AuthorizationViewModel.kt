package com.bogsnebes.effectivemobile.ui.authorization

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences =
        application.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun saveUserData(editText2Text: String, editText3Text: String, maskedEditTextText: String) {
        with(sharedPreferences.edit()) {
            putString("name", editText2Text)
            putString("surname", editText3Text)
            putString("phone_number", maskedEditTextText)
            apply()
        }
    }
}
