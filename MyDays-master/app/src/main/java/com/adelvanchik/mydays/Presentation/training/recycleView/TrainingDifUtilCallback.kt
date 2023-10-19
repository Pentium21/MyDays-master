package com.adelvanchik.mydays.Presentation.training.recycleView

import androidx.recyclerview.widget.DiffUtil
import com.adelvanchik.mydays.Domain.entity.Training

class TrainingDifUtilCallback: DiffUtil.ItemCallback<Training>() {
    override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
        return oldItem == newItem
    }
}