package com.poc.photosapp.data.remote

import com.poc.photosapp.data.models.PhotoRemoteItem
import retrofit2.http.GET

interface PhotoApiService {

    @GET("/albums/1/photos")
    suspend fun getPhotos(): List<PhotoRemoteItem>

}