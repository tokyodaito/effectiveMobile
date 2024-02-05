package com.bogsnebes.effectivemobile.ui.authorization

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences =
        application.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

    fun saveUserData(editText2Text: String, editText3Text: String, maskedEditTextText: String) {
        with(sharedPreferences.edit()) {
            putString("editText2", editText2Text)
            putString("editText3", editText3Text)
            putString("maskedEditText", maskedEditTextText)
            apply()
        }
    }
}
