package android.example.com.githubuserapp.api

import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.GithubUserListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getGithubUserList() : Call<List<GithubUser>>
}