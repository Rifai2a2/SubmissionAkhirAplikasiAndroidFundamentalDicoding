package com.dicoding.submissionakhir.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionakhir.database.FavoriteRepository
import com.dicoding.submissionakhir.database.FavoriteUser


class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getAllFavoriteUsers()
    }
}