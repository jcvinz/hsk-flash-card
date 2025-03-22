package com.hskflashcard.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface LearnedHSKWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearnedWord(word: LearnedHSKWord)

}