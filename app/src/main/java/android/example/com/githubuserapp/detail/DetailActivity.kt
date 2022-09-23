package android.example.com.githubuserapp.detail

import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ActivityDetailBinding
import android.example.com.githubuserapp.main.MainActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val user = intent.getParcelableExtra<GithubUser>(MainActivity.EXTRA_DATA) as GithubUser
        viewModel.getGithubUserDetail(user.login)

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
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.followerTabs, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) {
                " follower"
            } else {
                " following"
            }
        }.attach()

//        binding.apply {
//            detailPhoto.setImageResource(user.avatar)
//            tvDetailName.text = user.name
//            tvDetailUsername.text = user.username
//            tvDetailLocation.text = user.location
//            tvDetailCompany.text = user.company
//            tvDetailRepository.text = resources.getString(R.string.repository, user.repository)
//            tvDetailFollow.text = resources.getString(R.string.detail_follow, user.follower, user.following)
//        }
//        binding.detailPhoto.setImageResource(user.avatar)
//        binding.tvDetailName.text = user.name
//        binding.tvDetailUsername.text = user.username
//        binding.tvDetailLocation.text = user.location
//        binding.tvDetailCompany.text = user.company
//        binding.tvDetailRepository.text = resources.getString(R.string.repository, user.repository)
//        binding.tvDetailFollow.text = resources.getString(R.string.detail_follow, user.follower, user.following)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}