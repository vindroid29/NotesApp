package com.globant.notesapp.core.common

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface UiState<out T> {
    // Loading state
    data object Loading : UiState<Nothing>

    //Success data
    data class Success<T>(val data: T) : UiState<T>

    // Error or exception
    data class Error(val exception: Throwable? = null) : UiState<Nothing>

}

//Extension to convert result into uiState
fun <T> Flow<T>.asResult(): Flow<UiState<T>> {
    return this.map<T, UiState<T>> { UiState.Success(it) }         // Wrap data
        .onStart {
            emit(UiState.Loading)
            delay(3000) //TODO remove this when final impl is done
        }                 // Emit loading first
        .catch { emit(UiState.Error(it)) }      // Catch errors here!
}