package com.adelvanchik.mydays.Presentation.thoughts.recycleView

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.R

class ThoughtsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val data: TextView = view.findViewById(R.id.tv_data_training)
    val month: TextView = view.findViewById(R.id.tv_month_training)
    val year: TextView = view.findViewById(R.id.tv_year_training)
    val thoughts: TextView = view.findViewById(R.id.tv_thoughts)
    val background: ConstraintLayout = view.findViewById(R.id.background_thoughts)
}