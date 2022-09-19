package android.example.com.githubuserapp.main

import android.example.com.githubuserapp.api.ApiConfig
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.GithubUserListResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userList = MutableLiveData<List<GithubUser>>()
    val userList : LiveData<List<GithubUser>> = _userList

    init {
        displayUserList()
    }

    private fun displayUserList() {
        val client = ApiConfig.getApiService().getGithubUserList()
        client.enqueue(object : Callback<GithubUserListResponse> {
            override fun onResponse(call: Call<GithubUserListResponse>, response: Response<GithubUserListResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _userList.value = responseBody?.githubUserListResponse
                }
            }

            override fun onFailure(call: Call<GithubUserListResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}