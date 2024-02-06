package com.bogsnebes.effectivemobile.ui.information

import androidx.lifecycle.ViewModel
import com.bogsnebes.effectivemobile.model.database.dto.FavoriteProduct
import com.bogsnebes.effectivemobile.model.impl.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    fun toggleFavorite(productId: String) {
        val disposable = productRepository.isFavorite(productId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isFavorite ->
                if (isFavorite) {
                    productRepository.removeFavorite(FavoriteProduct(productId))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                } else {
                    productRepository.addFavorite(FavoriteProduct(productId))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                }
            }, { throwable ->
                // Обработка ошибки
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}