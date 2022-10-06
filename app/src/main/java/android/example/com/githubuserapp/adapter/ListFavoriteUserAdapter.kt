package android.example.com.githubuserapp.adapter

import android.example.com.githubuserapp.database.FavoriteUser
import android.example.com.githubuserapp.databinding.ItemFavoriteUserBinding
import android.example.com.githubuserapp.repository.FavoriteUserRepository
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListFavoriteUserAdapter(private val favoriteUserList: List<FavoriteUser>, private val favoriteUserRepository: FavoriteUserRepository) : RecyclerView.Adapter<ListFavoriteUserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    class ListViewHolder(var binding: ItemFavoriteUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val favoriteUser = favoriteUserList[position]
        Glide.with(holder.itemView.context)
            .load(favoriteUser.imgUrl)
            .into(holder.binding.avatarPhoto)
        holder.apply {
            binding.apply {
                tvUsername.text = favoriteUser.username
                tvType.text = favoriteUser.type
                btnDelete.setOnClickListener {
                    favoriteUserRepository.delete(favoriteUser)
                }
                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(favoriteUserList[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = favoriteUserList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }
}