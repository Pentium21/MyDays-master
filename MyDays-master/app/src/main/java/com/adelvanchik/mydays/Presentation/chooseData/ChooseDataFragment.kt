package com.adelvanchik.mydays.Presentation.chooseData

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Presentation.addEditDay.AddEditDayFragment
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentChooseDataBinding
import java.util.*

class ChooseDataFragment : Fragment() {

    private var _binding: FragmentChooseDataBinding? = null
    private val binding: FragmentChooseDataBinding
        get() = _binding ?: throw RuntimeException("FragmentMainMenuBinding == null")

    private val vm by lazy { ViewModelProvider(this)[ChooseDataViewModel::class.java] }
    private lateinit var dayForNextFragment: Day

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.isClickable = false
        binding.nextBtn.isEnabled = false

        chooseDataClickListener()
        nextButtonCLickListener()
        observers()
        backButtonClickListener()

        if (binding.dateTV.text != EMPTY_STRING) {
            vm.loadDayFromDataBase()
        }
    }

    private fun backButtonClickListener() {
        binding.btnBackInChooseData.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun chooseDataClickListener() {
        val c = Calendar.getInstance()
        val yearCurrent = c.get(Calendar.YEAR)
        val monthCurrent = c.get(Calendar.MONTH)
        val dayCurrent = c.get(Calendar.DAY_OF_MONTH)

        binding.chooseDataBtn.setOnClickListener {
            val dpd = DatePickerDialog(requireActivity(), { _, year, month, day ->
                vm.checkData(year = year, month = month, day = day,
                    yearCurrent = yearCurrent, monthCurrent = monthCurrent, dayCurrent = dayCurrent)
            }, yearCurrent, monthCurrent, dayCurrent)
            dpd.show()
        }

        binding.todayBtn.setOnClickListener {
            vm.createFormatDate(year = yearCurrent, month = monthCurrent, day = dayCurrent)
        }
    }

    private fun nextButtonCLickListener() {
        binding.nextBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fragment,
                    AddEditDayFragment.newInstance(dayForNextFragment))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun observers() {
        vm.dataLiveData.observe(viewLifecycleOwner) {
            binding.dateTV.text = it
        }
        vm.messageAboutErrorDayChoice.observe(viewLifecycleOwner) {
            showToastAboutNotCorrectDay()
        }
        vm.enabledNextButton.observe(viewLifecycleOwner) {
            nextButtonEnabled()
        }
        vm.dayForNextFragment.observe(viewLifecycleOwner) {
            dayForNextFragment = it
        }
    }

    private fun showToastAboutNotCorrectDay() {
        val text = resources.getText(R.string.this_day_has_not_come)
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun nextButtonEnabled() {
        binding.nextBtn.isClickable = true
        binding.nextBtn.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.dateTV.text = EMPTY_STRING
    }

    companion object {
        private const val EMPTY_STRING = ""

        fun newInstance(): ChooseDataFragment {
            return ChooseDataFragment()
        }
    }


}