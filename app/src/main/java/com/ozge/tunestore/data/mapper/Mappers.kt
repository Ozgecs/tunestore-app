package com.ozge.tunestore.data.mapper
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.data.model.room.ProductEntity

fun Product?.mapToProductUI(isFavorite: Boolean): ProductUI {
    return ProductUI(
        id = this?.id ?: 1,
        title = this?.title.orEmpty(),
        price = this?.price ?: 0.0,
        salePrice = this?.salePrice ?: 0.0,
        description = this?.description.orEmpty(),
        category = this?.category.orEmpty(),
        imageOne = this?.imageOne.orEmpty(),
        rate = this?.rate ?: 0.0,
        count = this?.count ?: 0,
        saleState = this?.saleState ?: false,
        isFavorite = isFavorite
    )
}

fun ProductUI.mapToProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        salePrice = salePrice,
        description = description,
        category = category,
        imageOne = imageOne,
        rate = rate,
        count = count,
        saleState = saleState
    )
}

fun ProductEntity.mapToProductUI(): ProductUI {
    return ProductUI(
        id = id ?: 1,
        title = title,
        price = price,
        salePrice = salePrice,
        description = description,
        category = category,
        imageOne = imageOne,
        rate = rate,
        count = count,
        saleState = saleState,
        isFavorite = true
    )
}