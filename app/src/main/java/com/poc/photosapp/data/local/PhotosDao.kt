package com.poc.photosapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.utilities.Constants

@Dao
interface PhotosDao{
    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    suspend fun getPhotosFromCache(): List<PhotoItem>

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun clearCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cachePhotosList(vararg photoItem: PhotoItem)
}
