package com.hskflashcard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hskflashcard.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    fun handleDataPopulation(context: Context) = viewModelScope.launch {
        userRepository.handleDataPopulation(context)
    }
}