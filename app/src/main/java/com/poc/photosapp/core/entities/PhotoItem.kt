package com.poc.photosapp.core.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.poc.photosapp.utilities.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_NAME)
data class PhotoItem (
    val albumId: Int? = null,
    @PrimaryKey
    val id: Int ? = null,
    val thumbnailUrl: String? = null,
    val title: String? = null,
    val url: String? = null,
): Serializable