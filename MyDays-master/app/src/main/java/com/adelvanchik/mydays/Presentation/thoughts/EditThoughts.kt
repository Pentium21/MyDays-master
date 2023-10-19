package com.adelvanchik.mydays.Presentation.thoughts

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
import com.adelvanchik.mydays.Domain.entity.Thoughts
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Presentation.addEditDay.AddEditDayFragment
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentAddEditThoughtsBinding
import com.adelvanchik.mydays.databinding.FragmentAddEditTrainingBinding
import java.util.*


class EditThoughts : Fragment() {

    private val vm by lazy {
        ViewModelProvider(requireActivity())[EditThoughtsViewModel::class.java]
    }

    private var _binding: FragmentAddEditThoughtsBinding? = null
    private val binding: FragmentAddEditThoughtsBinding
        get() = _binding ?: throw RuntimeException("FragmentAddEditThoughtsBinding==null")


    private lateinit var thoughts: Thoughts
    private var data = DEFAULT_DATA_VALUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEditThoughtsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        vm.initThoughts(data)
        observers()
        buttonListeners()
        thoughtsListener()
    }

    private fun observers() {
        vm.dataLiveData.observe(viewLifecycleOwner) {
            thoughts = it
            binding.etThoughts.setText(it.thoughts)
        }
        vm.dataStringLiveData.observe(viewLifecycleOwner) {
            binding.data.text = it
        }
    }

    private fun buttonListeners() {
        binding.btnBackThoughts.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnDeleteThoughtsDay.setOnClickListener {
            showDeleteMessage()
        }
    }

    private fun thoughtsListener() {
        binding.etThoughts.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                thoughts = thoughts.copy(thoughts = s.toString())
                vm.editThoughts(data, s.toString())
            }
        })
    }


    private fun showDeleteMessage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.editThoughts(dayId = data)
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

        fun newInstance(id: Int) = EditThoughts().apply {
            arguments = Bundle().apply {
                putInt(KEY_DATA, id)
            }
        }
    }
}