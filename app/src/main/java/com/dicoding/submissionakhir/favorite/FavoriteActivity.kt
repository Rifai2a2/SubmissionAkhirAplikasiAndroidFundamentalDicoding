package com.dicoding.submissionakhir.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionakhir.databinding.ActivityFavoriteBinding
import com.dicoding.submissionakhir.factory.ViewModelFactory
import com.dicoding.submissionakhir.response.ItemsItem

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : FavoriteAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)


        favoriteViewModel.getAllFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
                users.map {
                val item = ItemsItem( avatarUrl = it.avatarUrl, login = it.username)
                items.add(item)
            }
            adapter.submitList(items)


        }
        binding.rvFavorite.adapter = adapter

    }



    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
