package com.poc.photosapp.data.repository

import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.data.local.LocalDataSource
import com.poc.photosapp.data.remote.RemoteDataSource
import com.poc.photosapp.domain.repository.PhotosRepository
import javax.inject.Inject

class PhotosRepositoryImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : PhotosRepository {
    override suspend fun getPhotos(isForceUpdate: Boolean): List<PhotoItem> {

        val cachedPhotos = localDataSource.getPhotosFromCache()
        if (cachedPhotos.isNotEmpty() && !isForceUpdate)
            return cachedPhotos

        val photosFromServer = remoteDataSource.getPhotosFromServer()
        val listToBeCached = photosFromServer.map {
            it.asEntity()
        }
        localDataSource.clearCache()
        localDataSource.cachePhotosList(listToBeCached)
        return listToBeCached
    }
}