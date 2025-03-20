package com.hskflashcard.data.repo

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hskflashcard.data.HSKDataWorker
import com.hskflashcard.data.Resource
import com.hskflashcard.data.source.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val localDataSource: LocalDataSource
) :
    UserRepository {

    override suspend fun handleSignIn(idToken: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).await()
        emit(Resource.Success(Unit))
    }.catch { throwable ->
        emit(Resource.Error(throwable.localizedMessage ?: "Unknown error"))
    }

    override suspend fun handleDataPopulation(context: Context) {
        if (!localDataSource.getHasBeenPopulated()) {
            val workRequest = OneTimeWorkRequestBuilder<HSKDataWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                        .build()
                ).build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "HSK_DATA_POP",
                ExistingWorkPolicy.KEEP,
                workRequest
            )
        }
    }

}