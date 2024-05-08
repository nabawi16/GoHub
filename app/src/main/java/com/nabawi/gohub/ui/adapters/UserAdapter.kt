package com.nabawi.gohub.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nabawi.gohub.data.model.UserEntity
import com.nabawi.gohub.databinding.ItemUserBinding
import com.nabawi.gohub.ui.details.DetailActivity
import com.nabawi.gohub.utils.Constans.EXTRA_USER

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val listUser = ArrayList<UserEntity>()

    fun setAllData(data: List<UserEntity>) {
        listUser.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(private val view: ItemUserBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(user: UserEntity) {

            view.apply {
                tvItemUsername.text = user.username
            }

            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.ivItemFoto)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}