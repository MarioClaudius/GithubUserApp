package android.example.com.githubuserapp.detail

import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.databinding.ActivityDetailBinding
import android.example.com.githubuserapp.favorite.FavoriteActivity
import android.example.com.githubuserapp.helper.ViewModelFactory
import android.example.com.githubuserapp.main.MainActivity
import android.example.com.githubuserapp.main.MainViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        val githubUser = intent.getParcelableExtra<GithubUser?>(MainActivity.EXTRA_DATA)
        githubUser?.login
        val favoriteUser = intent.getParcelableExtra<FavoriteUser?>(FavoriteActivity.EXTRA_FAVORITE)
        val username : String = if (githubUser != null) githubUser.login else favoriteUser?.username.toString()

        Log.d("DetailActivity", username)
        val viewModelFactory = ViewModelFactory.getInstance(this@DetailActivity.application, username)
        viewModel = ViewModelProvider(this@DetailActivity, viewModelFactory)[DetailViewModel::class.java]
        viewModel.getGithubUserDetail(username)

        viewModel.githubUserDetail.observe(this) { detailGithubUser ->
            Glide.with(this)
                .load(detailGithubUser.avatarUrl)
                .into(binding.detailPhoto)
            binding.apply {
                tvDetailName.text = detailGithubUser.name
                tvDetailUsername.text = detailGithubUser.login
                tvDetailLocation.text = detailGithubUser.location
                tvDetailCompany.text = detailGithubUser.company
                tvDetailRepository.text = resources.getString(R.string.repository, detailGithubUser.publicRepos)
            }
            if (detailGithubUser.name == null) {
                binding.tvDetailName.visibility = View.GONE
            }
            if(detailGithubUser.location == null) {
                binding.tvDetailLocation.visibility = View.GONE
            }
            if (detailGithubUser.company == null) {
                binding.tvDetailCompany.visibility = View.GONE
            }
            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.followerTabs, binding.viewPager) { tab, position ->
                tab.text = if (position == 0) {
                    "${detailGithubUser.followers} follower"
                } else {
                    "${detailGithubUser.following} following"
                }
            }.attach()
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isError.observe(this) {
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show()
            viewModel.doneToastErrorInput()
        }

        viewModel.favoriteUserIsExist.observe(this) { favoriteUserIsExist ->
//            Log.d("OBSERVER JUMLAH", favoriteUserIsExist.toString())
            if(favoriteUserIsExist == null) {
                Log.d("OBSERVER FAB", "BOOLEANNYA NULL")
            }
            if (favoriteUserIsExist) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_baseline_favorite_24))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.ic_baseline_favorite_border_24))
            }
        }

        binding.fabFavorite.setOnClickListener {
            val favUser = favoriteUser
                ?: FavoriteUser(
                    id = githubUser!!.id,
                    imgUrl = githubUser.avatarUrl,
                    username = githubUser.login,
                    type = githubUser.type
                )
            if (binding.fabFavorite.drawable == ContextCompat.getDrawable(baseContext, R.drawable.ic_baseline_favorite_24)) {
                viewModel.deleteFavoriteUser(favUser)
            } else {
                viewModel.addFavoriteUser(favUser)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}