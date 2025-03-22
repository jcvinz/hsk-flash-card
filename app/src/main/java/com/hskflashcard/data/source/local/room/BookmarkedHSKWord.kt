package com.hskflashcard.data.source.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_hsk_word")
data class BookmarkedHSKWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hskWordId: Int,
    val examples: String = ""
)
