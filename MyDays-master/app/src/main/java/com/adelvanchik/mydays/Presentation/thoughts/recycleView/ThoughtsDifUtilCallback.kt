package com.adelvanchik.mydays.Presentation.thoughts.recycleView

import androidx.recyclerview.widget.DiffUtil
import com.adelvanchik.mydays.Domain.entity.Thoughts

class ThoughtsDifUtilCallback(): DiffUtil.ItemCallback<Thoughts>() {
    override fun areItemsTheSame(oldItem: Thoughts, newItem: Thoughts): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: Thoughts, newItem: Thoughts): Boolean {
        return oldItem == newItem
    }
}