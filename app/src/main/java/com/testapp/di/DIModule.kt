package com.testapp.di


import android.content.Context
import android.content.res.AssetManager
import com.testapp.data.CarRepositoryImpl
import com.testapp.data.source.LocalFileDataSource
import com.testapp.data.source.LocalFileImpl
import com.testapp.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
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
    fun provideLocalDataSource(assetManager: AssetManager): LocalFileDataSource {
        return LocalFileImpl(assetManager)
    }

    @Singleton
    @Provides
    fun provideCarRepository(
        localFile: LocalFileImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CarRepository {
        return CarRepositoryImpl(localFile, ioDispatcher)
    }

    @IoDispatcher
    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher
