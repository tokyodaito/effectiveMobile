package com.bogsnebes.effectivemobile.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogsnebes.effectivemobile.model.database.dao.FavoritesDao
import com.bogsnebes.effectivemobile.model.database.dto.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
