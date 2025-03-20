package com.hskflashcard.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HSKWord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hskWordDao(): HSKWordDao

}