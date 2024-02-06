package com.bogsnebes.effectivemobile.model.impl

import com.bogsnebes.effectivemobile.model.database.dao.FavoritesDao
import com.bogsnebes.effectivemobile.model.database.dto.FavoriteProduct
import com.bogsnebes.effectivemobile.model.network.ProductService
import com.bogsnebes.effectivemobile.model.network.response.ProductResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val favoritesDao: FavoritesDao
) {
    fun getProducts(): Single<ProductResponse> = productService.getProducts()

    fun isFavorite(productId: String): Single<Boolean> = favoritesDao.isFavorite(productId)

    fun addFavorite(favoriteProduct: FavoriteProduct): Completable =
        favoritesDao.addFavorite(favoriteProduct)

    fun removeFavorite(favoriteProduct: FavoriteProduct): Completable =
        favoritesDao.removeFavorite(favoriteProduct)

    fun countFavorites(): Single<Int> = favoritesDao.countFavorites()
}


