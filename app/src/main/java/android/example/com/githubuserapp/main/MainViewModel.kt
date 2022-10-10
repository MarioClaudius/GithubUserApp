package android.example.com.githubuserapp.main

import android.example.com.githubuserapp.api.ApiConfig
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.SearchUserResponse
import android.example.com.githubuserapp.settingpreferences.SettingPreferences
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userList = MutableLiveData<List<GithubUser>>()
    val userList : LiveData<List<GithubUser>> = _userList

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    val isDarkMode : LiveData<Boolean> = pref.getThemeSetting().asLiveData()

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
                _isError.value = true
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

    fun doneToastErrorInput() {
        _isError.value = false
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun checkIsDarkModeSetting() : Boolean? {
        return isDarkMode.value
    }
}