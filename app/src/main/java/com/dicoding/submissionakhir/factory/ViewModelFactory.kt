package com.dicoding.submissionakhir.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionakhir.database.FavoriteRepository
import com.dicoding.submissionakhir.database.Injection
import com.dicoding.submissionakhir.detail.DetailUserViewModel
import com.dicoding.submissionakhir.favorite.FavoriteViewModel
import com.dicoding.submissionakhir.main.MainViewModel
import com.dicoding.submissionakhir.theme.ThemeViewModel

class ViewModelFactory(private val favoriteRepository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(favoriteRepository) as T
        }
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(favoriteRepository) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favoriteRepository) as T
        }
        else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}