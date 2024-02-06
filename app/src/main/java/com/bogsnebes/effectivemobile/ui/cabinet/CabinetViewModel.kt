package com.bogsnebes.effectivemobile.ui.cabinet

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bogsnebes.effectivemobile.model.impl.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CabinetViewModel @Inject constructor(
    application: Application,
    private val productRepository: ProductRepository // Инжектируем репозиторий
) : AndroidViewModel(application) {
    private val _countOfFavorites = MutableLiveData<Int>()
    val countOfFavorites: LiveData<Int> = _countOfFavorites
    fun loadFavoritesCount() {
        productRepository.countFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ count ->
                _countOfFavorites.value = count
            }, { error ->
                error.printStackTrace()
            })
    }

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