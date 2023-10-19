package com.adelvanchik.mydays.Presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adelvanchik.mydays.Presentation.chooseData.ChooseDataFragment
import com.adelvanchik.mydays.Presentation.statistics.StatisticsFragment
import com.adelvanchik.mydays.Presentation.thoughts.ThoughtsFragment
import com.adelvanchik.mydays.Presentation.training.TrainingFragment
import com.adelvanchik.mydays.Presentation.writing.WritingFragment
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    val binding: FragmentMenuBinding
        get() = _binding ?: throw RuntimeException("FragmentMenuBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNav.selectedItemId = R.id.add_btn
        bottomNavClickListener()
    }

    private fun bottomNavClickListener() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.writing_btn -> {
                    launchFragment(R.id.container_menu_fragment, WritingFragment.newInstance())
                }
                R.id.diary_btn -> {
                    launchFragment(R.id.container_menu_fragment, ThoughtsFragment.newInstance())
                }
                R.id.add_btn -> {
                    launchFragment(R.id.main_container_fragment, ChooseDataFragment.newInstance())
                }
                R.id.training_btn -> {
                    launchFragment(R.id.container_menu_fragment, TrainingFragment.newInstance())
                }
                R.id.statistics_btn -> {
                    launchFragment(R.id.container_menu_fragment, StatisticsFragment.newInstance())
                }
            }
            true
        }
    }

    private fun launchFragment(idContainer: Int, fragment: Fragment) {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(idContainer, fragment)
            .addToBackStack(NAME_BACK_STACK)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val NAME_BACK_STACK = "menu_fragment"
        fun getNameBackStack(): String = NAME_BACK_STACK
    }

}