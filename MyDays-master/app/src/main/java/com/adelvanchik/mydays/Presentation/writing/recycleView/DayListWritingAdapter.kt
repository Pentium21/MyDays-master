package com.adelvanchik.mydays.Presentation.writing.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.R

class DayListWritingAdapter: ListAdapter<Day, DayWritingViewHolder>(DayWritingDifUtilCallback()) {

    var clickDayListener: ((day: Day)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWritingViewHolder {
        var layout: Int = DEFAULT_VALUE
        when(viewType) {
            PATTERN_WITH_ACHIEVEMENTS_AND_THOUGHTS ->
                layout = R.layout.pattern_writing_day_with_achievement_and_thoughts
            PATTERN_WITH_ONE_TEXT -> layout =  R.layout.pattern_writing_day_with_one_text
            PATTERN_WITHOUT_TEXT -> layout = R.layout.pattern_writing_day_without_text
            else -> throw RuntimeException("ViewType: $viewType incorrect")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
            )
        return DayWritingViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayWritingViewHolder, position: Int) {
        val day = getItem(position)
        with(holder) {

            backgroundDay.setOnClickListener {
                clickDayListener?.invoke(day)
            }

            val context = holder.tvData.context

            tvData.text = day.day.toString()
            tvMonth.text = context.getString(getIdStringMonthFromNumber(day.month.toInt()))
            tvYear.text = day.year.toString()

            if (day.health!=Day.DEFAULT_SHORT_VALUE) {
                ivHealth.setImageResource(getIdOfSmileForDrawable(day.health.toInt()))
                ivHealth.visibility = View.VISIBLE
            } else {
                ivHealth.visibility = View.INVISIBLE
            }
            if (day.thoughts!=Day.DEFAULT_STRING_VALUE) {
                tvThoughts.text = day.thoughts
                tvThoughts.visibility = View.VISIBLE
            } else {
                tvThoughts.visibility = View.INVISIBLE
            }
            if (day.achievements!=Day.DEFAULT_STRING_VALUE) {
                if (day.thoughts==Day.DEFAULT_STRING_VALUE) {
                    tvThoughts.text = day.achievements
                    tvThoughts.visibility = View.VISIBLE
                } else {
                    tvAchievements.text = day.achievements
                    tvAchievements.visibility = View.VISIBLE
                }
            } else {
                tvAchievements.visibility = View.INVISIBLE
            }

            if (day.durationOfSleep!=Day.DEFAULT_SHORT_VALUE) {
                val hour = context.getString(R.string.hour)
                tvDurationOfSleep.text = "${day.durationOfSleep}$hour"
                ivDurationOfSleep.visibility = View.VISIBLE
            } else {
                ivDurationOfSleep.visibility = View.INVISIBLE
                tvDurationOfSleep.text = Day.DEFAULT_STRING_VALUE
            }

            if (day.amountOfWater!=Day.DEFAULT_FLOAT_VALUE) {
                val liter = context.getString(R.string.liter)
                tvAmountOfWater.text = "${day.amountOfWater}$liter"
                ivAmountOfWater.visibility = View.VISIBLE
            } else {
                ivAmountOfWater.visibility = View.INVISIBLE
                tvAmountOfWater.text = Day.DEFAULT_STRING_VALUE
            }

            if (day.percOfCompletedTasks!=Day.DEFAULT_SHORT_VALUE) {
                val percentSymb = context.getString(R.string.percent)
                val percent = day.percOfCompletedTasks
                tvPercOfCompletedTasks.text = "${percent}$percentSymb"
                backgroundDay.setBackgroundResource(getIdColorForPercent(percent.toInt()))
                ivPercOfCompletedTasks.visibility = View.VISIBLE
            } else {
                ivPercOfCompletedTasks.visibility = View.INVISIBLE
                tvPercOfCompletedTasks.text = Day.DEFAULT_STRING_VALUE
                backgroundDay.setBackgroundResource(R.color.grow)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val day = getItem(position)
        return if (day.achievements!=Day.DEFAULT_STRING_VALUE &&
            day.thoughts!=Day.DEFAULT_STRING_VALUE) PATTERN_WITH_ACHIEVEMENTS_AND_THOUGHTS
        else if (day.achievements!=Day.DEFAULT_STRING_VALUE ||
            day.thoughts!=Day.DEFAULT_STRING_VALUE) PATTERN_WITH_ONE_TEXT
        else PATTERN_WITHOUT_TEXT
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


    private fun getIdOfSmileForDrawable(number: Int): Int {
        val smileList = listOf(
            R.drawable.ic_mood_1,
            R.drawable.ic_mood_2,
            R.drawable.ic_mood_3,
            R.drawable.ic_mood_4,
            R.drawable.ic_mood_5,
        )
        return smileList[number]
    }

    private fun getIdColorForPercent(number: Int): Int {
        return when(number) {
            in 0..39 -> R.color.effectiveness_1
            in 40..69 -> R.color.effectiveness_2
            else -> R.color.effectiveness_3
        }
    }

    companion object {
        private const val PATTERN_WITH_ACHIEVEMENTS_AND_THOUGHTS = 0
        private const val PATTERN_WITH_ONE_TEXT = 1
        private const val PATTERN_WITHOUT_TEXT = 2
        private const val DEFAULT_VALUE = -111
    }
}