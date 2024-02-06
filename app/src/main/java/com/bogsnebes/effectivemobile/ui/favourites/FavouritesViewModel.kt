package com.bogsnebes.effectivemobile.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.effectivemobile.R
import com.bogsnebes.effectivemobile.model.database.FavoriteProduct
import com.bogsnebes.effectivemobile.model.impl.ProductRepository
import com.bogsnebes.effectivemobile.ui.catalog.recycler.catalog.CatalogItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<CatalogItem>>()
    val products: LiveData<List<CatalogItem>> = _products

    private var originalProductList: List<CatalogItem>? = null

    private val compositeDisposable = CompositeDisposable()

    private fun loadProducts() {
        compositeDisposable.add(
            productRepository.getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ productResponse ->
                    val catalogItems = productResponse.items.map { productItem ->
                        val photos = when (productItem.id) {
                            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> listOf(
                                R.drawable.image_6,
                                R.drawable.image_5
                            )

                            "54a876a5-2205-48ba-9498-cfecff4baa6e" -> listOf(
                                R.drawable.image_1,
                                R.drawable.image_2
                            )

                            "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> listOf(
                                R.drawable.image_5,
                                R.drawable.image_6
                            )

                            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> listOf(
                                R.drawable.image_6,
                                R.drawable.image_5
                            )

                            "16f88865-ae74-4b7c-9d85-b68334bb97db" -> listOf(
                                R.drawable.image_3,
                                R.drawable.image_4
                            )

                            "26f88856-ae74-4b7c-9d85-b68334bb97db" -> listOf(
                                R.drawable.image_2,
                                R.drawable.image_3
                            )

                            "15f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
                                R.drawable.image_6,
                                R.drawable.image_1
                            )

                            "88f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
                                R.drawable.image_4,
                                R.drawable.image_3
                            )

                            "55f58865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
                                R.drawable.image_1,
                                R.drawable.image_5
                            )

                            else -> listOf<Int>() // Пустой список, если нет соответствия
                        }
                        CatalogItem(
                            id = productItem.id,
                            price = productItem.price.price,
                            discountPrice = productItem.price.priceWithDiscount,
                            discountPercentage = ((1 - productItem.price.priceWithDiscount.toDouble() / productItem.price.price.toDouble()) * 100).toString(),
                            productName = productItem.title,
                            productDescription = productItem.description,
                            rating = productItem.feedback.rating.toString(),
                            favorite = false, // Изначально статус избранного false
                            imageUrls = photos // Предполагается, что у вас есть логика для получения URL изображений
                        )
                    }
                    originalProductList = catalogItems
                    checkFavoritesAndUpdate(catalogItems)
                }, { error ->
                    error.printStackTrace()
                })
        )
    }

    private fun checkFavoritesAndUpdate(catalogItems: List<CatalogItem>) {
        val disposable = Observable.fromIterable(catalogItems)
            .flatMapSingle { catalogItem ->
                productRepository.isFavorite(catalogItem.id)
                    .subscribeOn(Schedulers.io())
                    .map { isFavorite -> Pair(catalogItem, isFavorite) }
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pairs ->
                val updatedCatalogItems = pairs.map { (catalogItem, isFavorite) ->
                    catalogItem.copy(favorite = isFavorite)
                }
                _products.value = updatedCatalogItems
                originalProductList = updatedCatalogItems
            }, { error ->
                error.printStackTrace()
            })

        compositeDisposable.add(disposable)
    }

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

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}