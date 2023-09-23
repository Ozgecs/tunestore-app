package com.ozge.tunestore.di

import com.ozge.tunestore.data.repository.ProductRepository
import com.ozge.tunestore.data.source.local.ProductDao
import com.ozge.tunestore.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService, productDao: ProductDao): ProductRepository =
        ProductRepository(productService, productDao)

}