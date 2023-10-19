package com.adelvanchik.mydays.Presentation.statistics.recycleView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.R

class ParametersVieHolder(view: View): RecyclerView.ViewHolder(view) {

    val tvWeight: TextView = view.findViewById(R.id.tv_weight)
    val tvHeight: TextView = view.findViewById(R.id.tv_height)
    val tvDay: TextView = view.findViewById(R.id.tv_data_parameters)
    val tvMonth: TextView = view.findViewById(R.id.tv_month_parameters)
    val tvYear: TextView = view.findViewById(R.id.tv_year_parameters)
}