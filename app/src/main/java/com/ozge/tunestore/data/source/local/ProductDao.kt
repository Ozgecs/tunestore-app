package com.ozge.tunestore.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozge.tunestore.data.model.room.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(product: ProductEntity)

    @Delete
    fun deleteFromFavorites(product: ProductEntity)
}