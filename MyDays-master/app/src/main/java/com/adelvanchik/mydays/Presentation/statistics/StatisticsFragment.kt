package com.adelvanchik.mydays.Presentation.statistics

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.Domain.entity.Parameters
import com.adelvanchik.mydays.Presentation.statistics.recycleView.ParametersListAdapter
import com.adelvanchik.mydays.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private val vm by lazy { ViewModelProvider(requireActivity())[StatisticsViewModel::class.java] }

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentStatisticsBinding = null")

    private lateinit var parametersListAdapter: ParametersListAdapter
    private lateinit var parameters: Parameters

    private var currentData = DEFAULT_DATA


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentStatisticsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.initVM()
        setupRecycleView()
        observers()
        parametersListeners()
        setupCallbackForDeleteParametersDay()
    }

    private fun setupCallbackForDeleteParametersDay() {
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
                val thoughts = parametersListAdapter.currentList[viewHolder.adapterPosition]
                showDeleteMessage(thoughts.data)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvParameters)
    }

    private fun observers() {
        vm.parametersLiveData.observe(viewLifecycleOwner) {
            currentData = it.data
            parameters = it

            if (it.height == Parameters.DEFAULT_VALUE && it.weight == Parameters.DEFAULT_VALUE) {
                return@observe
            }

            if (it.height != Parameters.DEFAULT_VALUE) binding.etHeight.setText(it.height.toString())
            else binding.etHeight.setText(EMPTY_STRING)
            if (it.weight != Parameters.DEFAULT_VALUE) binding.etWeight.setText(it.weight.toString())
            else binding.etWeight.setText(EMPTY_STRING)

        }

        vm.parametersList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) binding.allDayTittle.visibility = View.INVISIBLE
            else binding.allDayTittle.visibility = View.VISIBLE
            parametersListAdapter.submitList(it)
        }

        vm.currentData.observe(viewLifecycleOwner) {
            currentData = it
        }
    }

    private fun parametersListeners() {
        binding.etWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                parameters = if (s.toString().isNotEmpty())
                    parameters.copy(weight = s.toString().toInt())
                else parameters.copy(weight = Parameters.DEFAULT_VALUE)
                vm.updateParameters(parameters)
            }
        })

        binding.etHeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                parameters = if (s.toString().isNotEmpty())
                    parameters.copy(height = s.toString().toInt())
                else parameters.copy(height = Parameters.DEFAULT_VALUE)
                vm.updateParameters(parameters)
            }
        })
    }

    private fun showDeleteMessage(dayId: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                if (currentData == dayId) {
                    binding.etHeight.setText(EMPTY_STRING)
                    binding.etWeight.setText(EMPTY_STRING)
                }
                vm.deleteParameters(dayId)
            }
            .setNegativeButton("Нет") { _, _ ->
                parametersListAdapter.notifyDataSetChanged()
            }.create().show()
    }

    private fun setupRecycleView() {
        parametersListAdapter = ParametersListAdapter()
        binding.rvParameters.adapter = parametersListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DEFAULT_DATA = -7
        private const val EMPTY_STRING = ""

        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }
}