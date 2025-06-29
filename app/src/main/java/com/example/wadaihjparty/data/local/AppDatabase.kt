package com.example.wadaihjparty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wadaihjparty.data.local.dao.CakeDao
import com.example.wadaihjparty.data.local.dao.OrderHistoryDao
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import com.example.wadaihjparty.domain.model.Cake

@Database(entities = [OrderHistoryEntity::class, Cake::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun orderHistoryDao(): OrderHistoryDao
    abstract fun cakeDao(): CakeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wadai_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}