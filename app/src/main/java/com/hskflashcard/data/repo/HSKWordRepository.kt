package com.hskflashcard.data.repo

import com.hskflashcard.data.Resource
import com.hskflashcard.data.source.local.room.HSKWord
import kotlinx.coroutines.flow.Flow

interface HSKWordRepository {

    suspend fun getHSKWords(hskLevel: String): Flow<Resource<List<HSKWord>>>

    suspend fun getTotalLearnedWords(hskLevel: String): Flow<Resource<Int>>

    suspend fun saveToLearnedWords(wordId: Int): Flow<Resource<Unit>>

    suspend fun getExamples(word: String): Flow<Resource<String>>

    suspend fun saveToBookmarkedWords(wordId: Int, examples: String): Flow<Resource<Unit>>

    suspend fun removeFromBookmarkedWords(wordId: Int): Flow<Resource<Unit>>

    suspend fun getAllProgress(): Flow<Resource<Pair<Int, Int>>>
}