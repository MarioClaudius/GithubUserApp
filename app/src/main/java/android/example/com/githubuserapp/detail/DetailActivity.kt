package android.example.com.githubuserapp

import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ActivityDetailBinding
import android.example.com.githubuserapp.main.MainActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(MainActivity.EXTRA_DATA) as GithubUser

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
}