package com.example.fasihbook.data

import com.example.fasihbook.model.FakeBookDataSource
import com.example.fasihbook.model.FavoriteBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {

    private val favoriteBook = mutableListOf<FavoriteBook>()

    init {
        if (favoriteBook.isEmpty()) {
            FakeBookDataSource.dummyBook.forEach {
                favoriteBook.add(FavoriteBook(it, false))
            }
        }
    }

    fun getAllBooks(): Flow<List<FavoriteBook>> {
        return flowOf(favoriteBook)
    }

    fun getFavoriteBookById(bookId: Long): FavoriteBook {
        return favoriteBook.first {
            it.book.id == bookId
        }
    }

    fun updateFavoriteBook(bookId: Long, newFavoriteValue: Boolean): Flow<Boolean> {
        val index = favoriteBook.indexOfFirst { it.book.id == bookId }
        val result = if (index >= 0) {
            val favoriteBook1 = favoriteBook[index]
            favoriteBook[index] =
                favoriteBook1.copy(book = favoriteBook1.book, isFavorite = newFavoriteValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedFavoriteBook(): Flow<List<FavoriteBook>> {
        return getAllBooks()
            .map { favoriteBooks ->
                favoriteBooks.filter { favoritebook ->
                    favoritebook.isFavorite != false
                }
            }
    }

    fun searchBooks(query: String): Flow<List<FavoriteBook>>{
        return getAllBooks()
            .map { favoriteBooks ->
                favoriteBooks.filter { favoriteBook ->
                    favoriteBook.book.title.contains(query, ignoreCase = true)
                }
            }
    }


    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}