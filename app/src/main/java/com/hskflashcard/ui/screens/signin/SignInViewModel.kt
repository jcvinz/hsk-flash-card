package com.hskflashcard.ui.screens.signin

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.hskflashcard.R
import com.hskflashcard.data.Resource
import com.hskflashcard.data.repo.UserRepository
import com.hskflashcard.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor (private val userRepository: UserRepository) : ViewModel() {

    private val _signInState = MutableStateFlow<UiState<Unit>>(UiState.Idle())
    val signInState: StateFlow<UiState<Unit>> = _signInState.asStateFlow()

    fun signInWithGoogle(context: Context) = viewModelScope.launch {
        try {
            val credentialManager = CredentialManager.create(context)

            val gso =
                GetSignInWithGoogleOption.Builder(context.getString(R.string.default_web_client_id))
                    .setNonce("").build()

            val request = androidx.credentials.GetCredentialRequest.Builder()
                .addCredentialOption(gso)
                .build()

            val response = credentialManager.getCredential(context, request)

            userRepository.handleSignIn(getIdToken(response.credential)).collect {
                when (it) {
                    is Resource.Loading -> _signInState.value = UiState.Loading()
                    is Resource.Error -> _signInState.value = UiState.Error(it.message!!)
                    is Resource.Success -> _signInState.value = UiState.Success(it.data!!)
                }
            }
        } catch (e: GetCredentialException) {
            _signInState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            Log.e("SignInViewModel", e.localizedMessage ?: "Unknown error")
        }
    }

    private fun getIdToken(credential: Credential): String {
        return if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            googleIdTokenCredential.idToken
        } else {
            ""
        }
    }

}