package android.example.com.githubuserapp.api

import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.GithubUserListResponse
import android.example.com.githubuserapp.data.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getGithubUserList() : Call<List<GithubUser>>

    @GET("search/users")
    fun searchGithubUserList(@Query("q") username : String) : Call<SearchUserResponse>

    @GET("users/{username}/followers")
    fun getFollowerList(@Path("username") username: String) : Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowingList(@Path("username") username: String) : Call<List<GithubUser>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getGithubUserDetail(@Path("username") username: String) : Call<GithubUser>
}