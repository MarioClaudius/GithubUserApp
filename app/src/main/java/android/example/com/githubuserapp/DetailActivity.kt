package android.example.com.githubuserapp

import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ActivityDetailBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(MainActivity.EXTRA_DATA) as User

        binding.detailPhoto.setImageResource(user.avatar)
        binding.tvDetailName.text = user.name
        binding.tvDetailUsername.text = user.username
        binding.tvDetailLocation.text = user.location
        binding.tvDetailCompany.text = user.company
        binding.tvDetailRepository.text = "${user.repository} Repository"
        binding.tvDetailFollow.text = "${user.follower} follower - ${user.following} following"
    }
}