package android.example.com.githubuserapp

import android.content.Intent
import android.example.com.githubuserapp.adapter.ListUserAdapter
import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvGithubUser
        rvUser.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataUsername = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getIntArray(R.array.repository)
            val dataFollower = resources.getIntArray(R.array.followers)
            val dataFollowing = resources.getIntArray(R.array.following)
            val listUser = ArrayList<User>()
            for (i in dataUsername.indices) {
                val user = User(dataUsername[i], dataName[i], dataAvatar.getResourceId(i, -1), dataCompany[i], dataLocation[i], dataRepository[i], dataFollower[i], dataFollowing[i])
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.onItemClickCallback {
            override fun onItemClicked(data: User) {
                //selectedUser(data)
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun selectedUser(user: User) {
        Toast.makeText(this, "${user.name}", Toast.LENGTH_SHORT).show()
    }
}