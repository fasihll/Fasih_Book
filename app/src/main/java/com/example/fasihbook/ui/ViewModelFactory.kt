package com.example.fasihbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fasihbook.data.BookRepository
import com.example.fasihbook.ui.screen.detail.DetailBookViewModel
import com.example.fasihbook.ui.screen.favorite.FavoriteViewModel
import com.example.fasihbook.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BookRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow viewModel class"+ modelClass.name)
    }
}