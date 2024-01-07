package com.poc.photosapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.utilities.Constants

@Database(entities = [PhotoItem::class], version = 1, exportSchema = false)
abstract class PhotosDataBase : RoomDatabase() {


    abstract fun photosDao(): PhotosDao

    companion object {

        @Volatile
        private var INSTANCE: PhotosDataBase? = null

        private fun create(context: Context): PhotosDataBase {
            return Room.databaseBuilder(
                context,
                PhotosDataBase::class.java,
                Constants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getInstance(mContext: Context): PhotosDataBase {
            synchronized(this) {
                var instance: PhotosDataBase? = INSTANCE
                if (instance == null)
                    instance = create(context = mContext)
                INSTANCE = instance
                return instance
            }
        }

    }

}