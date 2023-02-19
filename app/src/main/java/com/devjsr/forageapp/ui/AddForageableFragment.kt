package com.devjsr.forageapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devjsr.forageapp.BaseApplication
import com.devjsr.forageapp.R
import com.devjsr.forageapp.databinding.FragmentAddForageableBinding
import com.devjsr.forageapp.model.Forageable
import com.devjsr.forageapp.ui.viewmodel.ForageableViewModel
import com.devjsr.forageapp.ui.viewmodel.ForageableViewModelFactory

class AddForageableFragment : Fragment() {

    private val navigationArgs: AddForageableFragmentArgs by navArgs()

    private var _binding: FragmentAddForageableBinding? = null
    private val binding get() = _binding!!

    private lateinit var forageable: Forageable

    private val viewModel : ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrievedForageable(id).observe(viewLifecycleOwner) {selectedForageable ->
                forageable = selectedForageable
                bindForageable(forageable)
            }
            binding.apply {
                deleteBtn.visibility = View.VISIBLE
                deleteBtn.setOnClickListener {
                    deleteForage(forageable)
                }
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addForageable()
            }
        }
    }

    private fun deleteForage(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        findNavController().navigate(
            R.id.action_addForageableFragment_to_forageableListFragment
        )
    }

    private fun addForageable() {
        if (isValidEntry()) {
            viewModel.addForageable(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.inSeasonCheckbox.isChecked,
                binding.notesInput.text.toString()
            )

            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun updateForageable() {
        if (isValidEntry()) {
            viewModel.updateForageable(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )

            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun bindForageable( forageable: Forageable) {
        binding.apply {
            nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = forageable.inSeason
            notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener { updateForageable() }
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}