package com.hskflashcard.data.source.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learned_hsk_word")
data class LearnedHSKWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hskWordId: Int
)
