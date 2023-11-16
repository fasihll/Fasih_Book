package com.example.fasihbook.di

import com.example.fasihbook.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}