package com.hskflashcard.ui.components.flashcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hskflashcard.data.Resource
import com.hskflashcard.data.repo.HSKWordRepository
import com.hskflashcard.data.source.local.room.HSKWord
import com.hskflashcard.ui.screens.MainScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val hskWordRepository: HSKWordRepository
) : ViewModel() {

    private val _words = MutableStateFlow<List<HSKWord>>(emptyList())

    private val _totalWords = MutableStateFlow<Int>(0)
    val totalWords: StateFlow<Int> = _totalWords.asStateFlow()

    private val _learnedWords = MutableStateFlow<Int>(0)
    val learnedWords: StateFlow<Int> = _learnedWords.asStateFlow()

    private val _activeWords = MutableStateFlow<List<HSKWord>>(emptyList())
    val activeWords: StateFlow<List<HSKWord>> = _activeWords.asStateFlow()

    private val _nextCardIndex = MutableStateFlow<Int>(VISIBLE_WORDS)
    val nextCardIndex: StateFlow<Int> = _nextCardIndex.asStateFlow()

    companion object {
        private const val VISIBLE_WORDS = 3
    }

    init {
        val hskLevel =
            savedStateHandle.toRoute<MainScreenType.FlashCard>().hskLevel.replace(" ", "-")
                .lowercase().plus("-word-list")
        fetchLearnedWords(hskLevel)
        fetchHskWords(hskLevel)
    }

    fun onSwiped(direction: FlashCardDirection, wordId: Int) {
        when (direction) {
            FlashCardDirection.LEFT -> {

            }

            FlashCardDirection.RIGHT -> {
                viewModelScope.launch {
                    hskWordRepository.saveToLearnedWords(wordId)
                }
            }
        }
    }

    private fun fetchLearnedWords(hskLevel: String) = viewModelScope.launch {
        hskWordRepository.getTotalLearnedWords(hskLevel).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let { data ->
                        _learnedWords.value = data
                    }
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun fetchHskWords(hskLevel: String) = viewModelScope.launch {
        hskWordRepository.getHSKWords(hskLevel).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let { data ->
                        _words.value = data
                        _totalWords.value = data.size
                        _activeWords.value = data.take(VISIBLE_WORDS)
                        _nextCardIndex.value = _activeWords.value.size
                    }
                }

                is Resource.Error -> {}
            }
        }
    }
}