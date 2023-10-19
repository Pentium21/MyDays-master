package com.adelvanchik.mydays.Presentation.writing.recycleView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.R

class DayWritingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvData: TextView = view.findViewById(R.id.tv_data_training)
    val tvMonth: TextView = view.findViewById(R.id.tv_month_training)
    val tvYear: TextView = view.findViewById(R.id.tv_year_training)
    val ivHealth: ImageView = view.findViewById(R.id.iv_health)

    val tvThoughts: TextView = view.findViewById(R.id.tv_thoughts)
    val tvAchievements: TextView = view.findViewById(R.id.tv_achievements)

    val tvDurationOfSleep: TextView = view.findViewById(R.id.tv_duration_of_sleep)
    val ivDurationOfSleep: ImageView = view.findViewById(R.id.iv_duration_of_sleep)

    val tvAmountOfWater: TextView = view.findViewById(R.id.tv_amount_of_water)
    val ivAmountOfWater: ImageView = view.findViewById(R.id.iv_amount_of_water)

    val tvPercOfCompletedTasks: TextView = view.findViewById(R.id.tv_perc_of_completed_tasks)
    val ivPercOfCompletedTasks: ImageView = view.findViewById(R.id.iv_perc_of_completed_tasks)

    val backgroundDay: ConstraintLayout = view.findViewById(R.id.background_day)

}