package com.adelvanchik.mydays.Presentation.thoughts

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.mydays.Presentation.thoughts.recycleView.ThoughtsListAdapter
import com.adelvanchik.mydays.R
import com.adelvanchik.mydays.databinding.FragmentDiaryBinding


class ThoughtsFragment : Fragment() {

    private val vm by lazy { ViewModelProvider(requireActivity())[ThoughtsViewModel::class.java] }

    private var _binding: FragmentDiaryBinding? = null
    private val binding: FragmentDiaryBinding
        get() = _binding ?: throw RuntimeException("FragmentDiaryBinding = null")

    private lateinit var thoughtsListAdapter: ThoughtsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDiaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        vm.listThoughts.observe(viewLifecycleOwner) {
            thoughtsListAdapter.submitList(it)
        }

        setupCallbackDeleteForThoughtsDay()
    }

    private fun setupCallbackDeleteForThoughtsDay() {
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
                val thoughts = thoughtsListAdapter.currentList[viewHolder.adapterPosition]
                showDeleteMessage(thoughts.data)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvThoughts)
    }

    private fun showDeleteMessage(dayId: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить?")
            .setPositiveButton("Да") { _, _ ->
                vm.editThoughts(dayId = dayId)
            }
            .setNegativeButton("Нет") { _, _ ->
                thoughtsListAdapter.notifyDataSetChanged()
            }.create().show()
    }

    private fun setupRecycleView() {
        thoughtsListAdapter = ThoughtsListAdapter()
        binding.rvThoughts.adapter = thoughtsListAdapter

        thoughtsListAdapter.clickInThoughtsListener = {
            launchFragment(R.id.container_menu_fragment, EditThoughts.newInstance(it))
        }
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
        fun newInstance(): ThoughtsFragment {
            return ThoughtsFragment()
        }
    }
}