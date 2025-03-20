package com.hskflashcard.data.source.local.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "hsk_word", indices = [Index(value = ["hskLevel"])])
data class HSKWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hskLevel: String,
    val simplified: String,
    val pinyin: String,
    val translation: String
)