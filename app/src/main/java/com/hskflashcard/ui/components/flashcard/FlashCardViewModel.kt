package com.hskflashcard.ui.components.flashcard

import android.util.Log
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val hskWordRepository: HSKWordRepository
) : ViewModel() {

    private var _nextCardIndex = VISIBLE_WORDS
    private val _words = MutableStateFlow<List<HSKWord>>(emptyList())

    private val _totalWords = MutableStateFlow<Int>(0)
    val totalWords: StateFlow<Int> = _totalWords.asStateFlow()

    private val _learnedWords = MutableStateFlow<Int>(0)
    val learnedWords: StateFlow<Int> = _learnedWords.asStateFlow()

    private val _activeWords = MutableStateFlow<List<HSKWord>>(emptyList())
    val activeWords: StateFlow<List<HSKWord>> = _activeWords.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _examples = MutableStateFlow<String>("")
    val examples: StateFlow<String> = _examples.asStateFlow()

    companion object {
        private const val VISIBLE_WORDS = 3
    }

    init {
        val hskLevel =
            savedStateHandle.toRoute<MainScreenType.FlashCard>().hskLevel.replace(" ", "-")
                .lowercase()
        fetchLearnedWords(hskLevel)
        fetchHskWords(hskLevel)
    }

    fun onAiButtonClicked(word: String) = viewModelScope.launch {
        hskWordRepository.getExamples(word).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _isLoading.update { true }
                }
                is Resource.Success -> {
                    _isLoading.update { false }
                    _examples.update { resource.data!! }
                }
                is Resource.Error -> {
                    _isLoading.update { false }
                }
            }
        }
    }

    fun onBookmarkClicked(isBookmarked: Boolean, wordId: Int) = viewModelScope.launch {
        Log.d("FlashCardViewModel", "onBookmarkClicked: $isBookmarked, $wordId")
        if (isBookmarked) {
            hskWordRepository.saveToBookmarkedWords(wordId, _examples.value).collect()
        } else {
            hskWordRepository.removeFromBookmarkedWords(wordId).collect()
        }
    }

    fun onSwiped(direction: FlashCardDirection, wordId: Int) {
        when (direction) {
            FlashCardDirection.LEFT -> {
                _examples.update { "" }
            }

            FlashCardDirection.RIGHT -> {
                _examples.update { "" }
                viewModelScope.launch {
                    hskWordRepository.saveToLearnedWords(wordId).collect {
                        when (it) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _learnedWords.update { it.plus(1) }
                            }
                            is Resource.Error<*> -> {}
                        }
                    }
                }
            }
        }
        _nextCardIndex++

        if (_nextCardIndex < _words.value.size) {
            _activeWords.update {
                _words.value.slice(_nextCardIndex - 2 .. _nextCardIndex)
            }
            Log.d("FlashCardViewModel", "${_activeWords.value}")
        } else {
            // EMPTY
        }
    }

    private fun fetchLearnedWords(hskLevel: String) = viewModelScope.launch {
        hskWordRepository.getTotalLearnedWords(hskLevel).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let { data ->
                        _learnedWords.update { data }
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
                        _words.update {  data }
                        _totalWords.update { data.size }
                        _activeWords.update { data.take(VISIBLE_WORDS) as MutableList<HSKWord> }
                        _nextCardIndex = _activeWords.value.size - 1
                    }
                }
                is Resource.Error -> {}
            }
        }
    }
}