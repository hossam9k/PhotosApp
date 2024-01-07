package com.poc.photosapp.domain.repository

import com.poc.photosapp.core.entities.PhotoItem


interface PhotosRepository {

    suspend fun getPhotos(isForceUpdate: Boolean = false): List<PhotoItem>

}