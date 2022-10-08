package android.example.com.githubuserapp.helper

import android.app.Application
import android.example.com.githubuserapp.detail.DetailViewModel
import android.example.com.githubuserapp.favorite.FavoriteViewModel
import android.example.com.githubuserapp.main.MainViewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(private val mApplication: Application, private val mId: String) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, id: String): ViewModelFactory {
            if (INSTANCE == null) {
                Log.d("factory instance", id)
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, id)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            Log.d("factory", mId)
            return DetailViewModel(mApplication, mId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}