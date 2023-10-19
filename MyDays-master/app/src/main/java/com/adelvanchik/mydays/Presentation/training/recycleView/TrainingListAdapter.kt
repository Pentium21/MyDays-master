package com.adelvanchik.mydays.Presentation.training.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.R

class TrainingListAdapter: ListAdapter<Training,TrainingViewHolder>(TrainingDifUtilCallback()) {

    var clickTraining: ((training: Training)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.pattern_training, parent, false
        )
        return TrainingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = getItem(position)

        val context = holder.tvTraining.context

        with(holder) {

            backgroundTraining.setOnClickListener {
                clickTraining?.invoke(training)
            }

            tvDay.text = training.day.toString()
            tvMonth.text = context.getString(getIdStringMonthFromNumber(training.month.toInt()))
            tvYear.text = training.year.toString()
            tvTraining.text = training.training
        }
    }

    private fun getIdStringMonthFromNumber(number: Int): Int {
        val monthList = listOf(
            0, // Поскольку нулевого месяца не будет, то создадим пустой элемент для удобства
            R.string.month_1,
            R.string.month_2,
            R.string.month_3,
            R.string.month_4,
            R.string.month_5,
            R.string.month_6,
            R.string.month_7,
            R.string.month_8,
            R.string.month_9,
            R.string.month_10,
            R.string.month_11,
            R.string.month_12,
        )
        return monthList[number]
    }
}