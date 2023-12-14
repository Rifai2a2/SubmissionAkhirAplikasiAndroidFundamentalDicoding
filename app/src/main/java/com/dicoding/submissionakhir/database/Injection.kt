package com.dicoding.submissionakhir.database


import android.content.Context
import com.dicoding.submissionakhir.api.ApiConfig
import com.dicoding.submissionakhir.theme.ThemePreferences
import com.dicoding.submissionakhir.theme.dataStore


object Injection {
    fun provideRepository(context: Context):FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val pref = ThemePreferences.getInstance(context.dataStore)

        return FavoriteRepository.getInstance(apiService, dao, pref)
    }
}