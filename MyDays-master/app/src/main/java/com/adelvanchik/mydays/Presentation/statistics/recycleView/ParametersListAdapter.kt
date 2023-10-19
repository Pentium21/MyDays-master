package com.adelvanchik.mydays.Presentation.statistics.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.R

class ParametersListAdapter :
    ListAdapter<Parameters, ParametersVieHolder>(ParametersDifUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersVieHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.pattern_parameters,
            parent,
            false
        )
        return ParametersVieHolder(view)
    }

    override fun onBindViewHolder(holder: ParametersVieHolder, position: Int) {
        val parameters = getItem(position)

        val context = holder.tvHeight.context

        val kg = context.getString(R.string.kg)
        val sm = context.getString(R.string.sm)

        with(holder) {

            if (parameters.height!=Parameters.DEFAULT_VALUE)
                tvHeight.text = "${parameters.height} $sm"
            else tvHeight.text = DEFAULT_VALUE
            if (parameters.weight!=Parameters.DEFAULT_VALUE)
                tvWeight.text = "${parameters.weight} $kg"
            else tvWeight.text = DEFAULT_VALUE
            tvDay.text = parameters.day.toString()
            tvMonth.text = context.getString(getIdStringMonthFromNumber(parameters.month.toInt()))
            tvYear.text = parameters.year.toString()
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

    companion object {
        private const val DEFAULT_VALUE = "-"
    }
}