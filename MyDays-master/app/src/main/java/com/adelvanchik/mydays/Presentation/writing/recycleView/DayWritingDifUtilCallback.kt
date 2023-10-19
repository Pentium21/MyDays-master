package com.adelvanchik.mydays.Presentation.writing.recycleView

import androidx.recyclerview.widget.DiffUtil
import com.adelvanchik.mydays.Domain.entity.Day

class DayWritingDifUtilCallback: DiffUtil.ItemCallback<Day>() {
    override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
        return oldItem == newItem
    }
}