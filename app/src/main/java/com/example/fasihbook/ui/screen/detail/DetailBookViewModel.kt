package com.example.fasihbook.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fasihbook.data.BookRepository
import com.example.fasihbook.model.Book
import com.example.fasihbook.model.FavoriteBook
import com.example.fasihbook.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBookViewModel(
    private val repository: BookRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteBook>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteBook>>
        get() = _uiState

    fun getFavoriteBookById(bookId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFavoriteBookById(bookId))
        }
    }

    fun addToFavorite(book: Book, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteBook(book.id, isFavorite)
        }
    }
}