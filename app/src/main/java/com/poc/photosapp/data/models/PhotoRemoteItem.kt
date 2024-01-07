package com.poc.photosapp.data.models

import com.poc.photosapp.core.entities.PhotoItem

data class PhotoRemoteItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
){
    fun asEntity(): PhotoItem{
        return PhotoItem(
            albumId = albumId,
            id = id,
            thumbnailUrl = thumbnailUrl,
            title = title,
            url = url,
            )
    }
}

