package com.example.questtask.ui.quest.recyclerview

import com.example.questtask.util.*
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questtask.R
import com.example.questtask.repository.room.Quest

class QuestAdapter : RecyclerView.Adapter<QuestViewHolder>() {
    var data = listOf<Quest>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.title
        when (item.difficulty){
            1 -> holder.title.setTextColor(Color.parseColor(VERY_EASY_COLOR))
            else -> holder.title.setTextColor(Color.WHITE)
        }
        holder.descShort.text = item.description_short
        holder.points.text = fromDifficultyToPointsString(item.difficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate((R.layout.quest_item), parent, false)
        return QuestViewHolder(view)
    }

    private fun fromDifficultyToPointsString(difficulty : Int?) : String{
        return if(difficulty != null){
            var points : Int = difficulty.times(10)
            points.toString()
        } else "Error"
        }
    }

class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val title : TextView = itemView.findViewById(R.id.quest_title)
    val descShort : TextView = itemView.findViewById(R.id.quest_desc_short)
    val points : TextView = itemView.findViewById((R.id.quest_pts))
}
