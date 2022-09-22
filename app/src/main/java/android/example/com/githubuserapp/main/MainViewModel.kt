package android.example.com.githubuserapp.main

import android.example.com.githubuserapp.api.ApiConfig
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.SearchUserResponse
import android.util.Log
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
        displayGithubUserList()
    }

    fun displayGithubUserList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUserList()
        client.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e("TEST", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchGithubUserList(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchGithubUserList(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(call: Call<SearchUserResponse>, response: Response<SearchUserResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userList.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("TEST", "onFailure: ${t.message.toString()}")
            }

        })
    }

}