package android.example.com.githubuserapp.adapter

import android.example.com.githubuserapp.data.GithubUser
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
        val githubUser = githubUserList[position]
        Glide.with(holder.itemView.context)
            .load(githubUser.avatarUrl)
            .into(holder.binding.avatarPhoto)
        holder.apply {
            binding.apply {
                tvUsername.text = githubUser.login
                tvType.text = githubUser.type
                itemView.setOnClickListener {
                    onItemClickCallBack?.onItemClicked(githubUserList[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = githubUserList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}