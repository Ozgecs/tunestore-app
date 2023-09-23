package com.ozge.tunestore.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozge.tunestore.data.model.room.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productsDao(): ProductDao
}