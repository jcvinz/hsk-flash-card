package com.hskflashcard.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class HomeViewModel @Inject constructor(
    private val hskWordRepository: HSKWordRepository
) : ViewModel() {

    private val _progress = MutableStateFlow<Pair<Int, Int>>(Pair(0,0))
    val progress: StateFlow<Pair<Int, Int>> = _progress.asStateFlow()

    init {
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