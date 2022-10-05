package android.example.com.githubuserapp.favorite

import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.databinding.ActivityFavoriteBinding
import android.example.com.githubuserapp.helper.ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvFavoriteUser: RecyclerView
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory.getInstance(this@FavoriteActivity.application)
        viewModel = ViewModelProvider(this@FavoriteActivity, viewModelFactory)[FavoriteViewModel::class.java]
    }

    private fun showFavoriteUserList(favoriteUserList: List<FavoriteUser>) {
        rvFavoriteUser.layoutManager = LinearLayoutManager(this)
    }
}