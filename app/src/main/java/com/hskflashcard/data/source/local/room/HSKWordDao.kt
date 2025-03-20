package com.hskflashcard.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HSKWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<HSKWord>)

    @Query("SELECT * FROM hsk_word WHERE hskLevel = :level")
    suspend fun getWordsByLevel(level: String): List<HSKWord>

}