package com.dicoding.submissionakhir.detail

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.submissionakhir.api.ApiConfig
import com.dicoding.submissionakhir.database.FavoriteRepository
import com.dicoding.submissionakhir.database.FavoriteUser
import com.dicoding.submissionakhir.response.DetailUserResponse
import com.dicoding.submissionakhir.response.ItemsItem
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class DetailUserViewModel(private val repository: FavoriteRepository) : ViewModel() {

    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> = _favoriteStatus

    private val _listReview = MutableLiveData<DetailUserResponse>()
    val listReview: LiveData<DetailUserResponse> = _listReview

    private val _listFollowing = MutableLiveData<List<ItemsItem?>>()
    val listFollowing : LiveData<List<ItemsItem?>> = _listFollowing

    private val _listFollower = MutableLiveData<List<ItemsItem?>>()
    val listFollower : LiveData<List<ItemsItem?>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

   


    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            repository.insertUser(favoriteUser)
            _favoriteStatus.postValue(true)
        }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            repository.deleteUser(favoriteUser)
            _favoriteStatus.postValue(false)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return repository.getFavoriteUserByUsername(username)
    }


    fun getDetailByUsername(query: String) {
        _isLoading.value = true
        val client = repository.getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

 companion object{
        private const val TAG = "DetailUserViewModel"

    }

}