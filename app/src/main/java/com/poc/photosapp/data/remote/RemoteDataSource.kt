package com.poc.photosapp.data.remote

import com.poc.photosapp.data.models.PhotoRemoteItem

interface RemoteDataSource {

    suspend fun getPhotosFromServer() : List<PhotoRemoteItem>

}