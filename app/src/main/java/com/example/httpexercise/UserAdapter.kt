package com.example.httpexercise

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val users: List<User>, private val clickListener: OnUserClickListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        val userAddress: TextView = itemView.findViewById(R.id.userAddress)
        val userThumbnail: ImageView = itemView.findViewById(R.id.userThumbnail)

        init {
            itemView.setOnClickListener {
                val user = users[adapterPosition]
                clickListener.onUserClick(user.address.geo.lat, user.address.geo.lng)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.apply {
            userName.text = user.name
            userEmail.text = user.email
            userAddress.text = "${user.address.street}, ${user.address.city}"
            Glide.with(itemView.context).load(buildFullImageUrl(user.avatar.thumbnail)).into(userThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
    private fun buildFullImageUrl(relativePath: String): String {
        val baseUrl = "https://lebavui.github.io"
        return "$baseUrl$relativePath"
    }
}