package com.jmanrique.testappand.data.di

import android.content.Context
import androidx.room.Room
import com.jmanrique.testappand.data.ProductRepositoryImpl
import com.jmanrique.testappand.data.local.AppDatabase
import com.jmanrique.testappand.data.local.ProductDao
import com.jmanrique.testappand.data.remote.FakeStoreApi
import com.jmanrique.testappand.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val DATABASE_NAME = "test_app_db"

    @Provides
    @Singleton
    fun provideFakeStoreApi(retrofit: Retrofit): FakeStoreApi {
        return retrofit.create(FakeStoreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}
