package android.example.com.githubuserapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("created_at")
	val createdAt: String?,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("updated_at")
	val updatedAt: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int?,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String?,

	@field:SerializedName("followers_url")
	val followersUrl: String?,

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("following")
	val following: Int?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("location")
	val location: String?,
): Parcelable

data class SearchUserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<GithubUser>
)
