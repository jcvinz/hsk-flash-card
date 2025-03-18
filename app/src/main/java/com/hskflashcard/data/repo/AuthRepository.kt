package com.hskflashcard.data.repo

import com.hskflashcard.data.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun handleSignIn(idToken: String) : Flow<Resource<Unit>>

}