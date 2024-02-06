package com.bogsnebes.effectivemobile.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favoriteProduct: FavoriteProduct): Completable

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE productId = :productId)")
    fun isFavorite(productId: String): Single<Boolean>

    @Delete
    fun removeFavorite(favoriteProduct: FavoriteProduct): Completable

    @Query("SELECT COUNT(*) FROM favorites")
    fun countFavorites(): Single<Int>
}


