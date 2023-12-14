package com.dicoding.submissionakhir.detail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.adapter.SectionsPagerAdapter
import com.dicoding.submissionakhir.database.FavoriteUser
import com.dicoding.submissionakhir.databinding.ActivityDetailUserBinding
import com.dicoding.submissionakhir.factory.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private var favoriteStatus: Boolean = false

    private val detailViewModel by viewModels<DetailUserViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.getStringExtra(EXTRA_USERNAME) != null) {
            val username = intent.getStringExtra(EXTRA_USERNAME)
            detailViewModel.getDetailByUsername(username.toString())
            detailViewModel.listReview.observe(this) { user ->

                Glide.with(this)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.ivAvatar)
                binding.tvName.text = user.name.toString()
                binding.tvUsername.text = user.login.toString()
                binding.tvFollowers.text = "${user.followers.toString()} Followers"
                binding.tvFollowing.text = "${user.following.toString()} Following"
            }


            binding.ivFavorite.setOnClickListener {
                if (username != null) {
                    val user = detailViewModel.listReview.value
                    if (user != null) {
                        val favoriteUser = FavoriteUser(username, user.avatarUrl)
                        if (favoriteStatus) {
                            detailViewModel.deleteFavoriteUser(favoriteUser)
                            Toast.makeText(this, getString(R.string.favorite_dihapus), Toast.LENGTH_SHORT).show()
                        } else {
                            detailViewModel.addFavoriteUser(favoriteUser)
                            Toast.makeText(this, getString(R.string.favorite_ditambahkan), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


            if (username != null) {
                detailViewModel.getFavoriteUserByUsername(username).observe(this){ favUser ->
                    if (favUser != null){
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                        favoriteStatus = true
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                        favoriteStatus = false
                    }
                }
            }

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = username.toString()
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }

    }

    companion object {
        const val EXTRA_USERNAME = "user.name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}