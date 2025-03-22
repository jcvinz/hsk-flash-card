package com.hskflashcard.data.repo

import com.hskflashcard.data.Resource
import com.hskflashcard.data.source.local.room.HSKWord
import com.hskflashcard.data.source.local.room.HSKWordDao
import com.hskflashcard.data.source.local.room.LearnedHSKWord
import com.hskflashcard.data.source.local.room.LearnedHSKWordDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HSKWordRepositoryImpl @Inject constructor(
    private val hskWordDao: HSKWordDao,
    private val learnedHSKWordDao: LearnedHSKWordDao
) : HSKWordRepository {

    override suspend fun getHSKWords(hskLevel: String): Flow<Resource<List<HSKWord>>> = flow {
        emit(Resource.Loading())
        val words = hskWordDao.getWordsByLevel(hskLevel)
        emit(Resource.Success(words))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Unknown error"))
    }

    override suspend fun getTotalLearnedWords(hskLevel: String): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        val totalLearnedWords = learnedHSKWordDao.getTotalLearnedWords(hskLevel)
        emit(Resource.Success(totalLearnedWords))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Unknown error"))
    }

    override suspend fun saveToLearnedWords(wordId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        learnedHSKWordDao.insertLearnedWord(LearnedHSKWord(hskWordId = wordId))
        emit(Resource.Success(Unit))
    }.catch {
        emit(Resource.Error(it.localizedMessage ?: "Unknown error"))
    }

}