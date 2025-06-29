package com.example.wadaihjparty.ui.costum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wadaihjparty.databinding.FragmentCustomBinding
import com.example.wadaihjparty.R

class CustomCakeFragment : Fragment() {
    private var _binding: FragmentCustomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CustomCakeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState )
        setupSpinners()
        binding.buttonAddCustomToCart.setOnClickListener {
            submitCustomCake()
        }
    }
    private fun setupSpinners() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cake_sizes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerSize.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cake_variant,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerVariants.adapter = adapter
        }
    }
    private fun submitCustomCake() {
        val selectedSize = binding.spinnerSize.selectedItem.toString()
        val selectedLayer = binding.spinnerVariants.selectedItem.toString()
        val theme = binding.editTextTheme.text.toString().trim()
        val notes = binding.editTextNotes.text.toString().trim()

        if (binding.spinnerSize.selectedItemPosition == 0 || binding.spinnerVariants.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Harap pilih ukuran dan jumlah lapisan", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.addCustomCakeToCart(selectedSize, selectedLayer, theme, notes)

        Toast.makeText(requireContext(), "Kue custom berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}