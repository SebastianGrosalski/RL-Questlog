package com.example.questtask.ui.addFriend.recyclerview

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questtask.R
import com.example.questtask.repository.classes.FriendRequest
import com.example.questtask.repository.firebase.FirebaseRepository

class RequestAdapter: RecyclerView.Adapter<RequestAdapter.RequestItemViewHolder>() {
    var data = mutableListOf<FriendRequest>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        val item = data[position]
        holder.requestFrom.text = item.senderEmail
        holder.btn_accept.setOnClickListener{
            FirebaseRepository.acceptFriendRequest(item)
            FirebaseRepository.createFriendship(item.sender, item.receiver)
            data.removeAt(position)
            this.notifyItemRemoved(position)
        }

        holder.btn_decline.setOnClickListener{
            FirebaseRepository.declineFriendRequest(item)
            data.removeAt(position)
            this.notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.request_item, parent, false)
        return RequestItemViewHolder(view)
    }

    class RequestItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val requestFrom: TextView = itemView.findViewById(R.id.tv_email)
        val btn_accept : ImageButton = itemView.findViewById(R.id.btn_acceptRequest)
        val btn_decline : ImageButton = itemView.findViewById(R.id.btn_declineRequest)
    }
}

