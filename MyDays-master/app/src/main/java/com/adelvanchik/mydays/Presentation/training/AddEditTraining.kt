package com.adelvanchik.mydays.Presentation.training

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.mydays.Domain.entity.Day
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Presentation.addEditDay.AddEditDayFragment
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentAddEditTrainingBinding
import java.util.*


class AddEditTraining : Fragment() {

    private val vm by lazy {
        ViewModelProvider(requireActivity())[TrainingAddEditViewModel::class.java]
    }

    private var _binding: FragmentAddEditTrainingBinding? = null
    private val binding: FragmentAddEditTrainingBinding
        get() = _binding ?: throw RuntimeException("FragmentAddEditTrainingBinding==null")


    private lateinit var training: Training
    private var data = DEFAULT_DATA_VALUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditTrainingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        initTrainingDay()
        observers()
        buttonListeners()
        trainingListener()
    }

    private fun initTrainingDay() {
        if (data == DEFAULT_DATA_VALUE) vm.initTrainingWithCurrentData()
        else vm.initTrainingWithData(data)
    }

    private fun observers() {
        vm.dataLiveData.observe(viewLifecycleOwner) {
            training = it
            binding.etTraining.setText(it.training)
        }
        vm.dataStringLiveData.observe(viewLifecycleOwner) {
            binding.data.text = it
        }
    }

    private fun buttonListeners() {
        binding.btnBackTraining.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnDeleteTrainingDay.setOnClickListener {
            showDeleteMessage()
        }
    }

    private fun trainingListener() {
        binding.etTraining.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                training = training.copy(training = s.toString())
                vm.editTraining(training)
            }
        })
    }


    private fun showDeleteMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.deleteTrainingDay(training)
                requireActivity().supportFragmentManager.popBackStack()
            }
            .setNegativeButton("Нет") { _, _ ->

            }.create().show()
    }

    private fun parseArgs() {
        requireArguments().getInt(KEY_DATA).let {
            data = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_DATA = "key for data"
        private const val DEFAULT_DATA_VALUE = -1

        fun newInstance(id: Int = DEFAULT_DATA_VALUE) = AddEditTraining().apply {
            arguments = Bundle().apply {
                putInt(KEY_DATA, id)
            }
        }
    }
}