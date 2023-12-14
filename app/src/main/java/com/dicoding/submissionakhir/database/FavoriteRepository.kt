package com.dicoding.submissionakhir.database

import androidx.lifecycle.LiveData
import com.dicoding.submissionakhir.api.ApiService
import com.dicoding.submissionakhir.response.DetailUserResponse
import com.dicoding.submissionakhir.response.GithubResponse
import com.dicoding.submissionakhir.theme.ThemePreferences
import kotlinx.coroutines.flow.Flow
import retrofit2.Call


class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val themePreferences: ThemePreferences

) {
    fun getThemeSetting(): Flow<Boolean> {
        return themePreferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        themePreferences.saveThemeSetting(isDarkModeActive)
    }


    suspend fun insertUser(favoriteUser: FavoriteUser) {
        favoriteDao.insert(favoriteUser)
    }

    suspend fun deleteUser(favoriteUser: FavoriteUser) {
        favoriteDao.delete(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteDao.getAllFavoriteUsers()
    }

    fun getListUsers(query: String): Call<GithubResponse> {
        return apiService.getListUsers(query)
    }
    fun getDetailUser(query: String): Call<DetailUserResponse> {
        return apiService.getDetailUser(query)
    }

    companion object {
        private const val TAG = "FavoriteRepository"

        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoriteDao,
            themePreferences: ThemePreferences

        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, newsDao,themePreferences)
            }.also { instance = it }
    }
}

