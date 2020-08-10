package com.example.questtask.ui.quest.recyclerview

import com.example.questtask.util.*
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questtask.R
import com.example.questtask.R.drawable.customborder_accepted
import com.example.questtask.R.drawable.customborder_done
import com.example.questtask.databinding.QuestItemBinding
import com.example.questtask.repository.room.Quest

class QuestAdapter(val clickListener : QuestListener) : ListAdapter<Quest, QuestAdapter.QuestViewHolder>(QuestDiffCallBack()) {

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = QuestItemBinding.inflate(layoutInflater, parent, false)
        return QuestViewHolder(binding)
    }

class QuestViewHolder(val binding: QuestItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(clickListener: QuestListener, item : Quest){
        binding.questTitle.text = item.title
        binding.quest = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
        when (item.difficulty){
            1 -> {
                binding.questTitle.setTextColor(Color.parseColor(VERY_EASY_COLOR))
            }
            2 -> {
                binding.questTitle.setTextColor(Color.parseColor(EASY_COLOR))
            }
            3 -> {
                binding.questTitle.setTextColor(Color.parseColor(MEDIUM_COLOR))
            }
            4 -> {
                binding.questTitle.setTextColor(Color.parseColor(HARD_COLOR))
            }
            5 -> {
                binding.questTitle.setTextColor(Color.parseColor(VERY_HARD_COLOR))
            }
            else -> binding.questTitle.setTextColor(Color.WHITE)
        }
        when(item.topic){
            DIET -> binding.imgTopic.setImageResource(R.drawable.ic_diet)
            KNOWLEDGE -> binding.imgTopic.setImageResource(R.drawable.ic_knowledge)
            FITNESS -> binding.imgTopic.setImageResource(R.drawable.ic_fitness)
            WORK -> binding.imgTopic.setImageResource(R.drawable.ic_work)
            HEALTH -> binding.imgTopic.setImageResource(R.drawable.ic_health)
            else -> binding.imgTopic.setImageResource(R.drawable.ic_tidiness)
        }
        when {
            item.accepted == true && item.done != true -> {
                binding.layout.setBackgroundResource(customborder_accepted)
            }
            item.done == true && item.accepted == true -> {
                binding.layout.setBackgroundResource(customborder_done)
            }
            else -> {
                binding.layout.setBackgroundColor(Color.parseColor("#232323"))
            }
        }

        binding.questDescShort.text = item.description_short
        binding.questPts.text = fromDifficultyToPointsString(item.difficulty)
    }

    private fun fromDifficultyToPointsString(difficulty : Int?) : String{
        return if(difficulty != null){
            var points : Int = difficulty.times(10)
            points.toString()
        } else "Error"
    }
}
}

class QuestDiffCallBack : DiffUtil.ItemCallback<Quest>(){
    override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean {
        return oldItem == newItem
    }
}

class QuestListener(val clickListener: (id : Int) -> Unit){
    fun onClick(quest: Quest) = clickListener(quest.id)
}