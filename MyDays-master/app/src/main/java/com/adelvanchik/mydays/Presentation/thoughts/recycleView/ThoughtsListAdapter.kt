package com.adelvanchik.mydays.Presentation.thoughts.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.R

class ThoughtsListAdapter: ListAdapter<Thoughts, ThoughtsViewHolder>(ThoughtsDifUtilCallback()) {

    var clickInThoughtsListener: ((data: Int)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThoughtsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pattern_thoughts,parent,false)
        return ThoughtsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThoughtsViewHolder, position: Int) {
        val dayThoughts = getItem(position)

        val context = holder.thoughts.context

        with(holder) {
            background.setOnClickListener {
                clickInThoughtsListener?.invoke(dayThoughts.data)
            }

            data.text = dayThoughts.day.toString()
            month.text = context.getString(getIdStringMonthFromNumber(dayThoughts.month.toInt()))
            year.text = dayThoughts.year.toString()
            thoughts.text = dayThoughts.thoughts
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