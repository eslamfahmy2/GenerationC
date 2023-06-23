package com.testapp.di


import android.content.Context
import android.content.res.AssetManager
import com.testapp.data.CarRepositoryImpl
import com.testapp.data.source.LocalFile
import com.testapp.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Singleton
    @Provides
    fun provideAssetsManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(assetManager: AssetManager): LocalFile {
        return LocalFile(assetManager)
    }

    @Singleton
    @Provides
    fun provideCarRepository(localFile: LocalFile): CarRepository {
        return CarRepositoryImpl(localFile)
    }
}