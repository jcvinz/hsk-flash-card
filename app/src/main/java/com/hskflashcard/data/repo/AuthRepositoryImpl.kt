package com.hskflashcard.data.repo

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hskflashcard.data.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :
    AuthRepository {

    override suspend fun handleSignIn(idToken: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        emit(Resource.Success(Unit))
    }.catch { throwable ->
        emit(Resource.Error(throwable.localizedMessage ?: "Unknown error"))
    }
}