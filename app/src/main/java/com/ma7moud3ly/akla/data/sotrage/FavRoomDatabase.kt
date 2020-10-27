package com.ma7moud3ly.akla.data.sotrage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavRecipe::class], version = 1, exportSchema = false)
abstract class FavRoomDatabase : RoomDatabase() {
    abstract fun dao(): FavRecipeDao?

    companion object {
        @Volatile
        private var INSTANCE: FavRoomDatabase? = null
        fun getDatabase(context: Context): FavRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FavRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            FavRoomDatabase::class.java, DB_NAME
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}