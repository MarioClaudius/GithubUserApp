package android.example.com.githubuserapp.api

import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.GithubUserListResponse
import android.example.com.githubuserapp.data.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getGithubUserList() : Call<List<GithubUser>>

    @GET("search/users")
    fun searchGithubUserList(@Query("q") username : String) : Call<SearchUserResponse>
}