package com.batikfy.batikfy.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.batikfy.batikfy.data.local.entity.ArticleEntity
import com.batikfy.batikfy.data.local.entity.BatikEntity

@Database(entities = [BatikEntity::class, ArticleEntity::class], version = 1, exportSchema = false)
abstract class BatikfyDatabase : RoomDatabase() {
    abstract fun batikfyDao(): BatikfyDao

    companion object {
        @Volatile
        private var instance:BatikfyDatabase? = null
        fun getInstance(context: Context): BatikfyDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BatikfyDatabase::class.java, "Batikfy.db"
                ).build()
            }
    }
}