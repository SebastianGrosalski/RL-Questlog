package com.example.questtask.ui.friendList.recyclerview

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questtask.R
import com.example.questtask.repository.classes.Friend
import com.example.questtask.repository.classes.FriendRequest
import com.example.questtask.repository.firebase.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class FriendListAdapter: RecyclerView.Adapter<FriendListAdapter.FriendItemViewHolder>() {
    val mAuth = Firebase.auth
    var data = mutableListOf<Friend>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        val item = data[position]
        holder.requestFromMail.text = item.email
        holder.requestFromUsername.text = item.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.friend_item, parent, false)
        return FriendItemViewHolder(view)
    }

    class FriendItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val requestFromMail: TextView = itemView.findViewById(R.id.tv_emailOfFriend)
        val requestFromUsername : TextView = itemView.findViewById(R.id.tv_nameOfFriend)
    }
}

