package com.example.fasihbook.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fasihbook.data.BookRepository
import com.example.fasihbook.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: BookRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAddedFavoriteBook() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedFavoriteBook()
                .collect { favoriteBook ->
                    _uiState.value = UiState.Success(FavoriteState(favoriteBook))
                }
        }
    }

    fun updateFavoriteBook(bookId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteBook(bookId, isFavorite)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedFavoriteBook()
                    }
                }
        }
    }
}