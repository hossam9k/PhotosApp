package com.poc.photosapp.data.local

import com.poc.photosapp.core.entities.PhotoItem
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(private val photosDao: PhotosDao) :
    LocalDataSource {
    override suspend fun getPhotosFromCache(): List<PhotoItem> {
        return photosDao.getPhotosFromCache()
    }

    override suspend fun clearCache() {
        photosDao.clearCache()
    }

    override suspend fun cachePhotosList(list: List<PhotoItem>) {
        // convert list before saved to DB
        photosDao.cachePhotosList(*list.toTypedArray())
    }
}