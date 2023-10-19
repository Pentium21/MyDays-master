package com.adelvanchik.mydays.Presentation.writing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.mydays.Domain.entity.FilterData
import com.adelvanchik.mydays.Domain.entity.FilterEffectiveness
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentFiltersForDaysBinding
import java.util.*


class FilterForDaysFragment : Fragment() {

    var closeFilterFragment: (() -> Unit)? = null

    private val vm by lazy {
        ViewModelProvider(requireActivity())[FilterForDaysViewModel::class.java]
    }

    private val c = Calendar.getInstance()
    private val yearCurrent = c.get(Calendar.YEAR)

    private var startYear = DEFAULT_START_VALUE
    private var endYear = DEFAULT_START_VALUE
    private var startMonth = DEFAULT_START_VALUE
    private var endMonth = DEFAULT_START_VALUE

    private var _binding: FragmentFiltersForDaysBinding? = null
    private val binding: FragmentFiltersForDaysBinding
        get() = _binding ?: throw RuntimeException("FragmentFiltersForDaysBinding == null")

    private var firstStartForFilterData = true
    private var firstStartForFilterEffectiveness = true

    private var filterData = FilterData()
    private var filterEffectiveness = FilterEffectiveness()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltersForDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("FilterForDaysFragment", "initial")

        vm.initValues()

        minYearInTableObserve()

        filterEffectivenessObserve()
        filterEffectivenessListeners()

        val monthList = arrayOf(
            getString(R.string.month_1),
            getString(R.string.month_2),
            getString(R.string.month_3),
            getString(R.string.month_4),
            getString(R.string.month_5),
            getString(R.string.month_6),
            getString(R.string.month_7),
            getString(R.string.month_8),
            getString(R.string.month_9),
            getString(R.string.month_10),
            getString(R.string.month_11),
            getString(R.string.month_12),
        )

        setValueForNPDATA(monthList)
        filterDataObserve()
        filterDataListeners(monthList)

        binding.btnCloseFilterFragment.setOnClickListener {
            closeFilterFragment?.invoke()
        }
    }

    private fun filterEffectivenessObserve() {
        vm.filterEffectivenessLiveData.observe(viewLifecycleOwner) {
            Log.d("FilterForDaysFragment", it.toString())
            if (firstStartForFilterEffectiveness) {
                filterEffectiveness = it
                binding.cbEffectivenessEmpty.isChecked = it.isIncludeEffectivenessEmpty
                binding.cbEffectivenessBefore40.isChecked = it.isIncludeEffectiveness1
                binding.cbEffectivenessMiddle.isChecked = it.isIncludeEffectiveness2
                binding.cbEffectivenessAfter70.isChecked = it.isIncludeEffectiveness3
                firstStartForFilterEffectiveness = false
            }
        }
    }

    private fun filterEffectivenessListeners() {
        with(binding) {
            cbEffectivenessEmpty.setOnCheckedChangeListener { _, isChecked ->
                filterEffectiveness = filterEffectiveness.copy(
                    isIncludeEffectivenessEmpty = isChecked)
                vm.saveDayFilterEffectiveness(filterEffectiveness)
            }
            cbEffectivenessBefore40.setOnCheckedChangeListener { _, isChecked ->
                filterEffectiveness = filterEffectiveness.copy(isIncludeEffectiveness1 = isChecked)
                vm.saveDayFilterEffectiveness(filterEffectiveness)
            }
            cbEffectivenessMiddle.setOnCheckedChangeListener { _, isChecked ->
                filterEffectiveness = filterEffectiveness.copy(isIncludeEffectiveness2 = isChecked)
                vm.saveDayFilterEffectiveness(filterEffectiveness)
            }
            cbEffectivenessAfter70.setOnCheckedChangeListener { _, isChecked ->
                filterEffectiveness = filterEffectiveness.copy(isIncludeEffectiveness3 = isChecked)
                vm.saveDayFilterEffectiveness(filterEffectiveness)
            }
        }
    }

    private fun setValueForNPDATA(monthList: Array<String>) {
        with(binding) {
            npFilterStartMonth.displayedValues = monthList
            npFilterStartMonth.minValue = 0
            npFilterStartMonth.maxValue = monthList.size - 1

            npFilterEndMonth.displayedValues = monthList
            npFilterEndMonth.minValue = 0
            npFilterEndMonth.maxValue = monthList.size - 1

            npFilterStartYear.maxValue = yearCurrent
            npFilterEndYear.maxValue = yearCurrent
        }
    }

    private fun filterDataObserve() {
        vm.filterDataLiveData.observe(viewLifecycleOwner) {
            Log.d("FilterForDaysFragment", it.toString())
            if (firstStartForFilterData) {
                filterData = it
                startMonth = it.monthStart
                endMonth = it.monthEnd
                startYear = it.yearStart
                endYear = it.yearEnd
                with(binding) {
                    npFilterStartYear.value = it.yearStart
                    npFilterStartMonth.value = it.monthStart
                    npFilterEndMonth.value = it.monthEnd
                    npFilterEndYear.minValue = it.yearStart
                    if (it.yearEnd == FilterData.DEFAULT_YEAR_END) {
                        npFilterEndYear.value = yearCurrent
                    } else {
                        npFilterEndYear.value = it.yearEnd
                    }
                }
                firstStartForFilterData = false
            }
        }
    }

    private fun filterDataListeners(monthList: Array<String>) {
        with(binding) {
            npFilterStartMonth.setOnValueChangedListener { _, _, newVal ->
                startMonth = newVal
                checkCurrentMonth()
                filterData = filterData.copy(monthStart = newVal)
                vm.saveDayFilterData(filterData)
            }
            npFilterEndMonth.setOnValueChangedListener { _, _, newVal ->
                if (startYear == endYear && newVal < startMonth) {
                    binding.npFilterStartMonth.value = newVal
                    startMonth = newVal
                    filterData = filterData.copy(monthStart = startMonth)
                }
                endMonth = newVal
                filterData = filterData.copy(monthEnd = newVal)
                vm.saveDayFilterData(filterData)
            }
            npFilterStartYear.setOnValueChangedListener { _, _, newVal ->
                startYear = newVal
                checkCurrentMonth()
                binding.npFilterEndYear.minValue = startYear
                filterData = filterData.copy(yearStart = newVal)
                vm.saveDayFilterData(filterData)
            }
            npFilterEndYear.setOnValueChangedListener { _, _, newVal ->
                endYear = newVal
                checkCurrentMonth()
                filterData = filterData.copy(yearEnd = newVal)
                vm.saveDayFilterData(filterData)
            }
        }
    }

    private fun checkCurrentMonth() {
        if (startMonth > endMonth && startYear == endYear) {
            binding.npFilterEndMonth.value = startMonth
            endMonth = startMonth
            filterData = filterData.copy(monthEnd = endMonth)
        }
    }

    private fun minYearInTableObserve() {
        vm.minYear.observe(viewLifecycleOwner) {
            val minYear = if (FilterForDaysViewModel.DEFAULT_MIN_VALUE == it) {
                yearCurrent
            } else {
                it
            }
            startYear = minYear.toInt()
            binding.npFilterStartYear.minValue = minYear.toInt()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_START_VALUE = 0

        fun newInstance() = FilterForDaysFragment()
    }

}