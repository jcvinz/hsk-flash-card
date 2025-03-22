package com.hskflashcard.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HSKWord::class, LearnedHSKWord::class, BookmarkedHSKWord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hskWordDao(): HSKWordDao

    abstract fun learnedHSKWordDao(): LearnedHSKWordDao

    abstract fun bookmarkedHSKWordDao(): BookmarkedHSKWordDao

}