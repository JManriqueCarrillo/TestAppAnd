package com.jmanrique.testappand.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val image: String
)
