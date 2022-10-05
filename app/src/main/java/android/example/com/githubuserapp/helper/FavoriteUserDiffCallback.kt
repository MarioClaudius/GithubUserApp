package android.example.com.githubuserapp.helper

import android.example.com.githubuserapp.database.FavoriteUser
import androidx.recyclerview.widget.DiffUtil

class FavoriteUserDiffCallback(private val mOldFavoriteUserList: List<FavoriteUser>, private val mNewFavoriteUserList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteUserList[oldItemPosition].id == mNewFavoriteUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = mNewFavoriteUserList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.imgUrl == newFavoriteUser.imgUrl && oldFavoriteUser.type == newFavoriteUser.type
    }
}