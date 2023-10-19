package com.adelvanchik.mydays.Presentation.training

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.Domain.entity.Training
import com.adelvanchik.mydays.Presentation.training.recycleView.TrainingListAdapter
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentTrainingBinding


class TrainingFragment : Fragment() {

    private val vm by lazy { ViewModelProvider(requireActivity())[TrainingViewModel::class.java] }

    private var _binding: FragmentTrainingBinding? = null
    val binding: FragmentTrainingBinding
        get() = _binding ?: throw RuntimeException("FragmentTrainingBinding==null")

    private lateinit var trainingListAdapter: TrainingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        observers()
        buttonListener()
        setupCallbackDeleteForTrainingDay()
    }

    private fun observers() {
        vm.trainingList.observe(viewLifecycleOwner) {
            trainingListAdapter.submitList(it)
        }
    }

    private fun buttonListener() {
        binding.btnAddTraining.setOnClickListener {
            launchFragment(R.id.container_menu_fragment, AddEditTraining.newInstance())
        }
    }

    private fun setupCallbackDeleteForTrainingDay() {
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
                val training = trainingListAdapter.currentList[viewHolder.adapterPosition]
                showDeleteMessage(training)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvTraining)
    }

    private fun showDeleteMessage(training: Training) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.deleteTrainingDay(training)
            }
            .setNegativeButton("Нет") { _, _ ->
                trainingListAdapter.notifyDataSetChanged()
            }.create().show()
    }

    private fun setupRecycleView() {
        trainingListAdapter = TrainingListAdapter()
        trainingListAdapter.clickTraining = {
            launchFragment(R.id.container_menu_fragment, AddEditTraining.newInstance(it.data))
        }
        binding.rvTraining.adapter = trainingListAdapter
    }

    private fun launchFragment(idContainer: Int, fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(idContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): TrainingFragment {
            return TrainingFragment()
        }
    }
}