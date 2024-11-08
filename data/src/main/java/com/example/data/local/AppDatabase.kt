package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [ExampleTable::class]
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "chat_app_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }

        }
    }

}


@Entity(tableName = "table")
data class ExampleTable(@PrimaryKey(autoGenerate = true) val id: Int)