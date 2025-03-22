package com.hskflashcard.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkedHSKWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkWord(word: BookmarkedHSKWord)

    @Query("DELETE FROM bookmarked_hsk_word WHERE hskWordId = :wordId")
    suspend fun removeWord(wordId: Int)

}