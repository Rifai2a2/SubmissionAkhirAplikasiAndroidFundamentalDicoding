package com.dicoding.submissionakhir.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.adapter.FollowAdapter
import com.dicoding.submissionakhir.database.UserUiState
import com.dicoding.submissionakhir.databinding.ActivityMainBinding
import com.dicoding.submissionakhir.factory.ViewModelFactory
import com.dicoding.submissionakhir.favorite.FavoriteActivity
import com.dicoding.submissionakhir.response.ItemsItem
import com.dicoding.submissionakhir.theme.ThemeActivity
import com.dicoding.submissionakhir.theme.ThemePreferences
import com.dicoding.submissionakhir.theme.dataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = ThemePreferences.getInstance(application.dataStore)
        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchUser(searchView.text.toString())
                    false
                }

            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.theme -> {
                        val intent = Intent(this@MainActivity, ThemeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UserUiState.Loading -> {
                    showLoading(true)
                }

                is UserUiState.Success -> {
                    setReviewData(uiState.data)
                    showLoading(false)
                }

                is UserUiState.Error -> {
                    Toast.makeText(this, uiState.error, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }


        private fun setReviewData(consumerReviews: List<ItemsItem?>) {
        val adapter = FollowAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
        }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}