package com.hskflashcard.utils

sealed class UiState<T> {
    class Idle<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val error: String) : UiState<T>()
}