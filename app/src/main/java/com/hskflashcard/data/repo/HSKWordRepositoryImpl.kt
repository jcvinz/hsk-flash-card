package com.hskflashcard.data.repo

import android.util.Log
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.type.Content
import com.google.gson.Gson
import com.hskflashcard.data.Resource
import com.hskflashcard.data.source.local.room.BookmarkedHSKWord
import com.hskflashcard.data.source.local.room.BookmarkedHSKWordDao
import com.hskflashcard.data.source.local.room.HSKWord
import com.hskflashcard.data.source.local.room.HSKWordDao
import com.hskflashcard.data.source.local.room.LearnedHSKWord
import com.hskflashcard.data.source.local.room.LearnedHSKWordDao
import com.hskflashcard.data.source.remote.HSKExamplesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HSKWordRepositoryImpl @Inject constructor(
    private val hskWordDao: HSKWordDao,
    private val learnedHSKWordDao: LearnedHSKWordDao,
    private val bookmarkedHSKWordDao: BookmarkedHSKWordDao,
    private val firebaseVertexAIModel: GenerativeModel
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

    override suspend fun getExamples(word: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val prompt = Content.Builder().text("Help me to understand this word: $word").build()
        val response = firebaseVertexAIModel.generateContent(prompt)
        val tokenResponse = firebaseVertexAIModel.countTokens(prompt)

        Log.d("HSKWordRepositoryImpl", response.text.toString())
        Log.d("HSKWordRepositoryImpl", "Total Tokens : ${tokenResponse.totalTokens}")
        Log.d(
            "HSKWordRepositoryImpl",
            "Total Billable Characters : ${tokenResponse.totalBillableCharacters}"
        )

        if (response.text != null) {
            val examplesResponse = Gson().fromJson(response.text, HSKExamplesResponse::class.java)
            emit(Resource.Success(examplesResponse.explanation + "\n\n" + examplesResponse.examples))
        } else {
            emit(Resource.Error("No response from AI model"))
        }
    }.catch { throwable ->
        Log.e("HSKWordRepositoryImpl", throwable.message.toString())
        emit(Resource.Error("Get examples failed: ${throwable.message}"))
    }

    override suspend fun saveToBookmarkedWords(
        wordId: Int,
        examples: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        bookmarkedHSKWordDao.bookmarkWord(BookmarkedHSKWord(hskWordId = wordId, examples = examples))
        Log.d("HSKWordRepositoryImpl", "Saved: $wordId, $examples")
        emit(Resource.Success(Unit))
    }.catch { throwable ->
        Log.e("HSKWordRepositoryImpl", throwable.message.toString())
        emit(Resource.Error(throwable.localizedMessage ?: "Unknown error"))
    }

    override suspend fun removeFromBookmarkedWords(wordId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        bookmarkedHSKWordDao.removeWord(wordId)
        Log.d("HSKWordRepositoryImpl", "Removed: $wordId")
        emit(Resource.Success(Unit))
    }.catch { throwable ->
        Log.e("HSKWordRepositoryImpl", throwable.message.toString())
        emit(Resource.Error(throwable.localizedMessage ?: "Unknown error"))
    }

}