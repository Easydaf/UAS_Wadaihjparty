package com.example.wadaihjparty.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wadaihjparty.R
import com.example.wadaihjparty.data.manager.SessionManager
import com.example.wadaihjparty.databinding.FragmentFirstPageBinding
import com.example.wadaihjparty.model.OnboardingItem
import com.example.wadaihjparty.ui.auth.AuthActivity
import com.google.android.material.tabs.TabLayoutMediator

class FirstPageFragment : Fragment() {
    private var _binding: FragmentFirstPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnboardingItems()
        binding.viewPager.adapter = onboardingAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.btnGetStarted.visibility = if (position == onboardingAdapter.itemCount - 1) View.VISIBLE else View.INVISIBLE
            }
        })

        binding.btnGetStarted.setOnClickListener {
            SessionManager.setOnboardingCompleted(requireContext(), true)
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setupOnboardingItems() {
        val onboardingItems = listOf(
            OnboardingItem(R.drawable.logo, "Selamat Datang", "Temukan berbagai macam kue lezat untuk acaramu"),
            OnboardingItem(R.drawable.logo, "Pesan Mudah", "Pilih kue favoritmu, pesan, dan kami akan siapkan."),
            OnboardingItem(R.drawable.logo, "Kualitas Terjamin", "Dibuat dengan bahan-bahan terbaik dan penuh cinta.")
        )
        onboardingAdapter = OnboardingAdapter(onboardingItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}