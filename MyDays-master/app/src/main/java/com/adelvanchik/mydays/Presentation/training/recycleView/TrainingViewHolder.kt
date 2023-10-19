package com.adelvanchik.mydays.Presentation.training.recycleView

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.R

class TrainingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvDay: TextView = view.findViewById(R.id.tv_data_training)
    val tvMonth: TextView = view.findViewById(R.id.tv_month_training)
    val tvYear: TextView = view.findViewById(R.id.tv_year_training)
    val tvTraining: TextView = view.findViewById(R.id.tv_training)
    val backgroundTraining: ConstraintLayout = view.findViewById(R.id.background_training)
}