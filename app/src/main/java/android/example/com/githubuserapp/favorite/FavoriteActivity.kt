package android.example.com.githubuserapp.favorite

import android.content.Context
import android.content.Intent
import android.example.com.githubuserapp.adapter.ListFavoriteUserAdapter
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.databinding.ActivityFavoriteBinding
import android.example.com.githubuserapp.detail.DetailActivity
import android.example.com.githubuserapp.helper.ViewModelFactory
import android.example.com.githubuserapp.repository.FavoriteUserRepository
import android.example.com.githubuserapp.settingpreferences.SettingPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvFavoriteUser: RecyclerView
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        val viewModelFactory = ViewModelFactory(this@FavoriteActivity.application, "", pref)
        viewModel = ViewModelProvider(this@FavoriteActivity, viewModelFactory)[FavoriteViewModel::class.java]

        viewModel.favoriteUserList.observe(this) { favoriteUserList ->
            if(favoriteUserList.size == 0) {
                binding.rvFavoriteUser.visibility = View.GONE
                binding.tvNoFavoriteUser.visibility = View.VISIBLE
            } else {
                binding.rvFavoriteUser.visibility = View.VISIBLE
                binding.tvNoFavoriteUser.visibility = View.GONE
                showFavoriteUserList(favoriteUserList)
            }
        }

        rvFavoriteUser = binding.rvFavoriteUser
        rvFavoriteUser.setHasFixedSize(true)
    }

    private fun showFavoriteUserList(favoriteUserList: List<FavoriteUser>) {
        rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        val listFavoriteUserAdapter = ListFavoriteUserAdapter(favoriteUserList, FavoriteUserRepository(this@FavoriteActivity.application))
        rvFavoriteUser.adapter = listFavoriteUserAdapter

        listFavoriteUserAdapter.setOnItemClickCallback(object : ListFavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(EXTRA_FAVORITE, data)
                startActivity(intentToDetail)
            }
        })
    }

    companion object {
        const val EXTRA_FAVORITE = "extra-favorite"
    }
}