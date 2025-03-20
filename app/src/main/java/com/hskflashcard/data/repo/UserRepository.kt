package com.hskflashcard.data.repo

import android.content.Context
import com.hskflashcard.data.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun handleSignIn(idToken: String) : Flow<Resource<Unit>>

    suspend fun handleDataPopulation(context: Context)

}