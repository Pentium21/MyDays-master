package com.adelvanchik.mydays.Presentation.citation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.mydays.databinding.FragmentCitationBinding

class CitationFragment : Fragment() {

    private val vm: CitationViewModel by lazy {
        ViewModelProvider(this)[CitationViewModel::class.java]
    }

    private var _binding: FragmentCitationBinding? = null
    private val binding: FragmentCitationBinding
        get() = _binding ?: throw RuntimeException("FragmentMainMenuBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding =  FragmentCitationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.citation.observe(viewLifecycleOwner) {
            binding.citationTV.text = it.citation
            binding.authorTV.text = it.author
        }
        binding.citationTV.setOnClickListener {
            vm.generateCitation()
        }
    }

    override fun onStart() {
        super.onStart()
        vm.generateCitation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance(): CitationFragment {
            return CitationFragment()
        }
    }

}