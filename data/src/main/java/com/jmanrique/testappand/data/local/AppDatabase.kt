package com.jmanrique.testappand.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmanrique.testappand.data.local.entities.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
