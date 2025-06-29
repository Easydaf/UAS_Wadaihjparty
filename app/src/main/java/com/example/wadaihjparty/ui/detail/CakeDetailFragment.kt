package com.example.wadaihjparty.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.wadaihjparty.databinding.FragmentCakeDetailBinding
import com.example.wadaihjparty.domain.model.Cake

class CakeDetailFragment : Fragment() {

    private var _binding: FragmentCakeDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCakeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cake: Cake? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("cake", Cake::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("cake")
        }

        cake?.let { selectedCake ->
            binding.tvCakeDetailName.text = selectedCake.name
            binding.tvCakeDesc.text = selectedCake.description
            binding.tvHistoryPrice.text = selectedCake.formattedPrice

            Glide.with(requireContext())
                .load(selectedCake.imageUrl)
                .into(binding.ivCakeImage)

            binding.btnAdd.setOnClickListener {
                viewModel.addToCart(selectedCake)

                Toast.makeText(requireContext(), "${selectedCake.name} berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}