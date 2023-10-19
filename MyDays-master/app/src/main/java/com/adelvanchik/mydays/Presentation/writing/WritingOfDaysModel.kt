package com.adelvanchik.mydays.Presentation.writing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adelvanchik.mydays.R

class WritingOfDaysModel : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pattern_writing_day_with_achievement_and_thoughts, container, false)
    }

}