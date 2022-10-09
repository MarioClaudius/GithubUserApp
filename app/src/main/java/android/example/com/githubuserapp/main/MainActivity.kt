package android.example.com.githubuserapp.main

import android.app.SearchManager
import android.content.Intent
import android.example.com.githubuserapp.detail.DetailActivity
import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.adapter.ListUserAdapter
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.databinding.ActivityMainBinding
import android.example.com.githubuserapp.favorite.FavoriteActivity
import android.example.com.githubuserapp.helper.ViewModelFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(this@MainActivity.application, "")
        viewModel = ViewModelProvider(this@MainActivity, viewModelFactory)[MainViewModel::class.java]

        viewModel.userList.observe(this) { githubUserList ->
            showRecyclerList(githubUserList)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isError.observe(this) {
            Toast.makeText(this, "Data not found!", Toast.LENGTH_SHORT).show()
            viewModel.doneToastErrorInput()
        }

        rvUser = binding.rvGithubUser
        rvUser.setHasFixedSize(true)
    }

    private fun showRecyclerList(githubUserList : List<GithubUser>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(githubUserList)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra(EXTRA_DATA, data)
                startActivity(intentToDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.search -> {
                val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
                val searchItem : MenuItem = item
                val searchView = searchItem.actionView as SearchView

                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.queryHint = resources.getString(R.string.search_hint)

                searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                        viewModel.displayGithubUserList()
                        return true
                    }

                })

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.searchGithubUserList(query.toString())
                        searchView.clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
                return true
            }
            R.id.favorite -> {
                val intentToFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
                return true
            }
            R.id.setting -> {
                Log.d("SETTING", "TEST MENU SETTING")
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}