package com.example.questtask.ui.progress

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.questtask.R
import com.example.questtask.databinding.FragmentProgressBinding
import com.example.questtask.util.*

class ProgressFragment : Fragment() {

    private lateinit var progressViewModel: ProgressViewModel
    private lateinit var binding: FragmentProgressBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_progress,
            container,
            false
        )
        progressViewModel = ViewModelProvider(this).get(ProgressViewModel::class.java)

        fun levelRatioStringOf(pointsKey : String, levelKey : String) : String{
            return "${progressViewModel.prefProvider.getPoints(pointsKey)}/" +
                    "${progressViewModel.prefProvider.calculateLevelBarrier(levelKey)}"
        }

        fun levelDependentColor(levelKey : String) : Int{
            return when(progressViewModel.prefProvider.getLevel(levelKey)){
                in 0..4 -> Color.parseColor(VERY_EASY_COLOR)
                in 5..9 -> Color.parseColor(EASY_COLOR)
                in 10..14 -> Color.parseColor(MEDIUM_COLOR)
                in 15..19 -> Color.parseColor(HARD_COLOR)
                else -> Color.parseColor(VERY_HARD_COLOR)
            }
        }

        //###### Setup for all progressbars ######//
        binding.tvName.text = progressViewModel.prefProvider.getName()
        //Mainlevel
        binding.tvLvl.text = progressViewModel.prefProvider.getLevel(LEVEL).toString()
        binding.tvLvl.setTextColor(levelDependentColor(LEVEL))
        binding.progressBarMain.max = progressViewModel.prefProvider.calculateLevelBarrier(LEVEL)
        binding.progressBarMain.progress = progressViewModel.prefProvider.getPoints(POINTS)
        binding.tvPbMain.text = levelRatioStringOf(POINTS, LEVEL)
        //Tidiness
        binding.tvTidinessLvl.text = progressViewModel.prefProvider.getLevel(TIDINESS_LVL).toString()
        binding.tvTidinessLvl.setTextColor(levelDependentColor(TIDINESS_LVL))
        binding.pbTidiness.max = progressViewModel.prefProvider.calculateLevelBarrier(TIDINESS_LVL)
        binding.pbTidiness.progress = progressViewModel.prefProvider.getPoints(TIDINESS_POINTS)
        binding.tvPbTidiness.text = levelRatioStringOf(TIDINESS_POINTS, TIDINESS_LVL)
        //Work
        binding.tvWorkLvl.text = progressViewModel.prefProvider.getLevel(WORK_LVL).toString()
        binding.tvWorkLvl.setTextColor(levelDependentColor(WORK_LVL))
        binding.pbWork.max = progressViewModel.prefProvider.calculateLevelBarrier(WORK_LVL)
        binding.pbWork.progress = progressViewModel.prefProvider.getPoints(WORK_POINTS)
        binding.tvPbWork.text = levelRatioStringOf(WORK_POINTS, WORK_LVL)
        //Health
        binding.tvHealthLvl.text = progressViewModel.prefProvider.getLevel(HEALTH_LVL).toString()
        binding.tvHealthLvl.setTextColor(levelDependentColor(HEALTH_LVL))
        binding.pbHealth.max = progressViewModel.prefProvider.calculateLevelBarrier(HEALTH_LVL)
        binding.pbHealth.progress = progressViewModel.prefProvider.getPoints(HEALTH_POINTS)
        binding.tvPbHealth.text = levelRatioStringOf(HEALTH_POINTS, HEALTH_LVL)
        //Fitness
        binding.tvFitnessLvl.text = progressViewModel.prefProvider.getLevel(FITNESS_LVL).toString()
        binding.tvFitnessLvl.setTextColor(levelDependentColor(FITNESS_LVL))
        binding.pbFitness.max = progressViewModel.prefProvider.calculateLevelBarrier(FITNESS_LVL)
        binding.pbFitness.progress = progressViewModel.prefProvider.getPoints(FITNESS_POINTS)
        binding.tvPbFitness.text = levelRatioStringOf(FITNESS_POINTS, FITNESS_LVL)
        //Diet
        binding.tvDietLvl.text = progressViewModel.prefProvider.getLevel(DIET_LVL).toString()
        binding.tvDietLvl.setTextColor(levelDependentColor(DIET_LVL))
        binding.pbDiet.max = progressViewModel.prefProvider.calculateLevelBarrier(DIET_LVL)
        binding.pbDiet.progress = progressViewModel.prefProvider.getPoints(DIET_POINTS)
        binding.tvPbDiet.text = levelRatioStringOf(DIET_POINTS, DIET_LVL)
        //Knowledge
        binding.tvKnowledgeLvl.text = progressViewModel.prefProvider.getLevel(KNOWLEDGE_LVL).toString()
        binding.tvKnowledgeLvl.setTextColor(levelDependentColor(KNOWLEDGE_LVL))
        binding.pbKnowledge.max = progressViewModel.prefProvider.calculateLevelBarrier(KNOWLEDGE_LVL)
        binding.pbKnowledge.progress = progressViewModel.prefProvider.getPoints(KNOWLEDGE_POINTS)
        binding.tvPbKnowledge.text = levelRatioStringOf(KNOWLEDGE_POINTS, KNOWLEDGE_LVL)


        return binding.root
    }
}
