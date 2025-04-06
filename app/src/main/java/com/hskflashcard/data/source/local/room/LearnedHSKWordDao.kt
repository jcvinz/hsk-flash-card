package com.hskflashcard.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LearnedHSKWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearnedWord(word: LearnedHSKWord)

    @Query("SELECT COUNT() FROM learned_hsk_word INNER JOIN hsk_word ON learned_hsk_word.hskWordId = hsk_word.id WHERE hsk_word.hskLevel = :hskLevel")
    suspend fun getTotalLearnedWords(hskLevel: String): Int

    @Query("SELECT COUNT() FROM learned_hsk_word")
    suspend fun getTotalLearnedWords(): Int

}