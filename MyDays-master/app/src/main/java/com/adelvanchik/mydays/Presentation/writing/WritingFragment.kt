package com.adelvanchik.mydays.Presentation.writing

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Presentation.addEditDay.AddEditDayFragment
import com.adelvanchik.mydays.Presentation.writing.recycleView.DayListWritingAdapter
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentWritingBinding


class WritingFragment : Fragment() {

    private val vm by lazy { ViewModelProvider(requireActivity())[WritingViewModel::class.java] }
    private lateinit var dayListWritingAdapter: DayListWritingAdapter

    private var _binding: FragmentWritingBinding? = null
    private val binding: FragmentWritingBinding
        get() = _binding ?: throw RuntimeException("FragmentWritingBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWritingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setOnClickListenerInDay()
        workFilterFragment()

        vm.dayList.observe(viewLifecycleOwner) {
            dayListWritingAdapter.submitList(it)
        }
        vm.notifyFilterDataLiveData.observe(viewLifecycleOwner) {
            vm.changeFilterData()
        }
        vm.notifyFilterEffectivenessLiveData.observe(viewLifecycleOwner) {
            vm.changeFilterData()
        }

        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val day = dayListWritingAdapter.currentList[viewHolder.adapterPosition]
                showDeleteMessage(day)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvWriting)

    }

    private fun showDeleteMessage(day: Day) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.deleteDay(day)
            }
            .setNegativeButton("Нет") { _, _ ->
                dayListWritingAdapter.notifyDataSetChanged()
            }.create().show()
    }


    private fun setOnClickListenerInDay() {
        dayListWritingAdapter.clickDayListener = { day ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fragment,
                    AddEditDayFragment.newInstance(day))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun workFilterFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (vm.getValueFromOpenFilterLiveData()!!) {
                        vm.changeConditionFilterFragment()
                        closeFilterFragment()
                    } else {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            })

        binding.btnOpenCloseFilters.setOnClickListener {
            val isFilterFragmentOpen = vm.getValueFromOpenFilterLiveData()
            if (isFilterFragmentOpen!!) {
                closeFilterFragment()
                Log.d("FilterForDaysFragment", "Close")
            } else {
                loadFilterFragment()
                Log.d("FilterForDaysFragment", "Open")
            }
            vm.changeConditionFilterFragment()
        }
    }

    private fun loadFilterFragment() {
        val instanceFilterFragment = vm.getInstanceFilterFragment()
        instanceFilterFragment!!.closeFilterFragment = {
            closeFilterFragment()
            vm.changeConditionFilterFragment()
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment_for_filters, instanceFilterFragment)
            .addToBackStack(KEY_FILTER_FRAGMENT_STACK)
            .commit()
        Log.d("FilterForDaysFragment", instanceFilterFragment.toString())
    }

    private fun closeFilterFragment() {
        requireActivity().supportFragmentManager.popBackStack(
            KEY_FILTER_FRAGMENT_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun setupRecycleView() {
        dayListWritingAdapter = DayListWritingAdapter()
        with(binding.rvWriting) {
            adapter = dayListWritingAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_FILTER_FRAGMENT_STACK = "key_filter_fragment"

        fun newInstance(): WritingFragment {
            return WritingFragment()
        }
    }

}