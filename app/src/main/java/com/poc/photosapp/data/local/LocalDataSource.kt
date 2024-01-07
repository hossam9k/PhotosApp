package com.poc.photosapp.data.local

import com.poc.photosapp.core.entities.PhotoItem

interface LocalDataSource {

    suspend fun getPhotosFromCache(): List<PhotoItem>

    suspend fun clearCache()

    suspend fun cachePhotosList(list: List<PhotoItem>)
}