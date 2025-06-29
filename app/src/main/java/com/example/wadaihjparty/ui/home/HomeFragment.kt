package com.example.wadaihjparty.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wadaihjparty.ui.costum.CustomCakeFragment
import com.example.wadaihjparty.ui.detail.CakeDetailFragment
import com.example.wadaihjparty.R
import com.example.wadaihjparty.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var cakeAdapter: CakeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("HomeFragment", "Swipe to refresh dipanggil.")
            viewModel.refreshCakes()
        }
        binding.btnCustomCake.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CustomCakeFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        cakeAdapter = CakeAdapter(emptyList()) { selectedCake ->
            val fragment = CakeDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("cake", selectedCake)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerViewKue.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerViewKue.adapter = cakeAdapter
    }

    private fun observeViewModel() {
        viewModel.cakeList.observe(viewLifecycleOwner) { cakes ->
            cakeAdapter.updateData(cakes)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}