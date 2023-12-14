package com.dicoding.submissionakhir.database


sealed class UserUiState<out R> private constructor() {
    data class Success<out T>(val data: T) : UserUiState<T>()
    data class Error(val error: String) : UserUiState<Nothing>()
    object Loading : UserUiState<Nothing>()
}

