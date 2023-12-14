package com.dicoding.submissionakhir.main

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.submissionakhir.database.FavoriteRepository
import com.dicoding.submissionakhir.database.UserUiState
import com.dicoding.submissionakhir.response.GithubResponse
import com.dicoding.submissionakhir.response.ItemsItem
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val repository: FavoriteRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState<List<ItemsItem?>>>()
    val uiState: LiveData<UserUiState<List<ItemsItem?>>> = _uiState

    fun getThemeSetting(): LiveData<Boolean> {
        return repository.getThemeSetting().asLiveData()
    }


    init{
        searchUser(USER_ID)
    }

    fun searchUser(query: String) {
        _uiState.value = UserUiState.Loading
        val client = repository.getListUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    _uiState.value = UserUiState.Success(response.body()?.items as List<ItemsItem?>)
                } else {
                    _uiState.value = UserUiState.Error(response.message())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _uiState.value = UserUiState.Error(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_ID = "rifai"
    }
}

