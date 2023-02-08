package com.devjsr.forageapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.devjsr.forageapp.R
import com.devjsr.forageapp.databinding.FragmentForageableListBinding
import com.devjsr.forageapp.ui.adapter.ForageableListAdapter
import com.devjsr.forageapp.ui.viewmodel.ForageableViewModel


class ForageableListFragment : Fragment() {

    private val viewModel: ForageableViewModel by activityViewModels()

    private var _binding: FragmentForageableListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForageableListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ForageableListAdapter { forageable ->
            val action = ForageableListFragmentDirections
                .actionForageableListFragmentToForageableDetailFragment(forageable.id)
            findNavController().navigate(action)
        }

        binding.apply {
            recyclerView.adapter = adapter
            addForageableFab.setOnClickListener {
                findNavController().navigate(R.id.action_forageableListFragment_to_addForageableFragment)
            }
        }
    }


}
