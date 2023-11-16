package com.example.fasihbook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fasihbook.data.BookRepository
import com.example.fasihbook.model.FavoriteBook
import com.example.fasihbook.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BookRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteBook>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<FavoriteBook>>>
        get() = _uiState

    fun getAllBooks(query: String?) {
        viewModelScope.launch {
            if (query != null){
                repository.searchBooks(query)
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { favoriteBook ->
                        _uiState.value = UiState.Success(favoriteBook)
                    }
            }else{
                repository.getAllBooks()
                    .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                    }
                    .collect { favoriteBook ->
                        _uiState.value = UiState.Success(favoriteBook)
                    }
            }
        }
    }
}