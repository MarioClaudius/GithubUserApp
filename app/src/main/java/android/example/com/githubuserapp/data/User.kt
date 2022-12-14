package android.example.com.githubuserapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var name: String,
    var avatar: Int,
    var company: String,
    var location: String,
    var repository: Int,
    var follower: Int,
    var following: Int
) : Parcelable
