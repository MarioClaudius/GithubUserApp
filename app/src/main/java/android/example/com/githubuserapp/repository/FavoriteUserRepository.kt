package android.example.com.githubuserapp.repository

import android.app.Application
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.database.FavoriteUserDao
import android.example.com.githubuserapp.database.FavoriteUserDatabase
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers() : LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String) : LiveData<Boolean> {
        Log.d("REPO", username)
        return mFavoriteUserDao.findFavoriteUserByUsername(username)
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }
}