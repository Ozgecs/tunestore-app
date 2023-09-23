package com.ozge.tunestore.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "imageOne")
    val imageOne: String,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "count")
    val count: Int,

    @ColumnInfo(name = "saleState")
    val saleState: Boolean
)