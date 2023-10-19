package com.adelvanchik.mydays.Presentation.statistics.recycleView

import androidx.recyclerview.widget.DiffUtil
import com.adelvanchik.mydays.Domain.entity.Parameters

class ParametersDifUtilCallback: DiffUtil.ItemCallback<Parameters>() {
    override fun areItemsTheSame(oldItem: Parameters, newItem: Parameters): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: Parameters, newItem: Parameters): Boolean {
        return oldItem == newItem
    }
}