package com.poc.photosapp.di

import android.content.Context
import com.poc.photosapp.core.usecases.PhotosUseCaseImp
import com.poc.photosapp.data.local.LocalDataSource
import com.poc.photosapp.data.local.LocalDataSourceImp
import com.poc.photosapp.data.local.PhotosDao
import com.poc.photosapp.data.local.PhotosDataBase
import com.poc.photosapp.data.remote.PhotoApiService
import com.poc.photosapp.data.remote.RemoteDataSource
import com.poc.photosapp.data.remote.RemoteDataSourceImp
import com.poc.photosapp.data.repository.PhotosRepositoryImp
import com.poc.photosapp.domain.repository.PhotosRepository
import com.poc.photosapp.domain.usecases.PhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): PhotoApiService =
        retrofit.create(PhotoApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: PhotoApiService): RemoteDataSource =
        RemoteDataSourceImp(service)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PhotosDataBase =
        PhotosDataBase.getInstance(context)

    @Provides
    @Singleton
    fun provideDao(photosDataBase: PhotosDataBase): PhotosDao = photosDataBase.photosDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(photosDao: PhotosDao): LocalDataSource =
        LocalDataSourceImp(photosDao)


    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): PhotosRepository = PhotosRepositoryImp(localDataSource, remoteDataSource)


    @Provides
    @Singleton
    fun provideUserCase(photosRepository: PhotosRepository): PhotosUseCase =
        PhotosUseCaseImp(photosRepository)


}