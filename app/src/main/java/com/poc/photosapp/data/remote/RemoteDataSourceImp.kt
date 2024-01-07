package com.poc.photosapp.data.remote

import com.poc.photosapp.data.models.PhotoRemoteItem
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(private val photoApiService: PhotoApiService) :
    RemoteDataSource {

    override suspend fun getPhotosFromServer(): List<PhotoRemoteItem> {
        return photoApiService.getPhotos()
    }

}