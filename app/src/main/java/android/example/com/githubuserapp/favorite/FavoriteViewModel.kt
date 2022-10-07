package android.example.com.githubuserapp.favorite

import android.app.Application
import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.repository.FavoriteUserRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)

    val favoriteUserList : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}