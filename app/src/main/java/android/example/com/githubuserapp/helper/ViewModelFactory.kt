package android.example.com.githubuserapp.helper

import android.app.Application
import android.example.com.githubuserapp.detail.DetailViewModel
import android.example.com.githubuserapp.favorite.FavoriteViewModel
import android.example.com.githubuserapp.main.MainViewModel
import android.example.com.githubuserapp.settingpreferences.SettingPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val mApplication: Application, private val mUsername: String, private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication, mUsername) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}