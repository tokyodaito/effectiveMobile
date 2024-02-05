package com.bogsnebes.effectivemobile.ui.cabinet

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel

class CabinetViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun getName(): String? {
        return sharedPreferences.getString("name", "name")
    }

    fun getSurname(): String? {
        return sharedPreferences.getString("surname", "surname")
    }

    fun getPhone(): String? {
        return sharedPreferences.getString("phone_number", "phone_number")
    }
}