package android.example.com.githubuserapp.adapter

import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.data.GithubUser
import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ItemGithubUserBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserAdapter(private val githubUserList: List<GithubUser>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onItemClickCallBack: OnItemClickCallback? = null

    class ListViewHolder(var binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val ()
        val githubUser = githubUserList[position]
        Glide.with(holder.itemView.context)
            .load(githubUser.avatarUrl)
            .into(holder.binding.avatarPhoto)
        holder.apply {
            binding.apply {
                tvName.text = githubUser.login
                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(githubUserList[holder.adapterPosition])
                }
            }
        }
        holder.binding.tvName.text = githubUser.login
//        holder.apply {
//            binding.apply {
////                avatarPhoto.setImageResource(avatar)
//                Glide.with(holder.itemView.context)
//                    .load(githubUser.avatarUrl)
//                    .into()
//                tvUsername.text = githubUser.login
//                tvName.text = githubUser.
//                tvLocation.text = location
////                tvFollow.text = itemView.resources.getString(R.string.detail_follow, follower, following)
//                itemView.setOnClickListener {
//                    onItemClickCallBack.onItemClicked(githubUserList[holder.adapterPosition])
//                }
//            }
//        }
    }

    override fun getItemCount(): Int = githubUserList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}