package android.example.com.githubuserapp.adapter

import android.example.com.githubuserapp.R
import android.example.com.githubuserapp.data.User
import android.example.com.githubuserapp.databinding.ItemGithubUserBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    class ListViewHolder(var binding: ItemGithubUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (username, name, avatar, _,  location, _, follower, following) = listUser[position]
        holder.apply {
            binding.apply {
                avatarPhoto.setImageResource(avatar)
                tvUsername.text = username
                tvName.text = name
                tvLocation.text = location
                tvFollow.text = itemView.resources.getString(R.string.detail_follow, follower, following)
                itemView.setOnClickListener {
                    onItemClickCallBack.onItemClicked(listUser[holder.adapterPosition])
                }
            }
        }
//        holder.binding.avatarPhoto.setImageResource(avatar)
//        holder.binding.tvUsername.text = username
//        holder.binding.tvName.text = name
//        holder.binding.tvLocation.text = location
//        holder.binding.tvFollow.text = "$follower follower - $following following"
//        holder.itemView.setOnClickListener {
//            onItemClickCallBack.onItemClicked(listUser[holder.adapterPosition])
//        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}