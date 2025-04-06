package com.hskflashcard.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hskflashcard.data.Resource
import com.hskflashcard.data.repo.HSKWordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val hskWordRepository: HSKWordRepository
) : ViewModel() {

    private val _imageUrl = MutableStateFlow<String>("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _progress = MutableStateFlow<Pair<Int, Int>>(Pair(0,0))
    val progress: StateFlow<Pair<Int, Int>> = _progress.asStateFlow()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    init {
        firebaseAuth.currentUser?.let { user ->
            _imageUrl.update { user.photoUrl.toString() }
            _name.update { user.displayName.toString() }
            _email.update { user.email.toString() }
        }
        viewModelScope.launch {
            hskWordRepository.getAllProgress().collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        it.data?.let { data -> _progress.update { data } }
                    }
                    is Resource.Error -> {}
                }
            }
        }
    }
}