package android.example.com.githubuserapp.detail

import android.example.com.githubuserapp.api.ApiConfig
import android.example.com.githubuserapp.data.GithubUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    private val _githubUserDetail = MutableLiveData<GithubUser>()
    val githubUserDetail : LiveData<GithubUser> = _githubUserDetail

    private val _followList = MutableLiveData<List<GithubUser>>()
    val followList : LiveData<List<GithubUser>> = _followList

    fun getGithubUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUserDetail(username)
        client.enqueue(object: Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _githubUserDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun displayFollowerList(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowerList(username)
        client.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _followList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun displayFollowingList(username : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingList(username)
        client.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _followList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun doneToastErrorInput() {
        _isError.value = false
    }
}