package com.example.wadaihjparty.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wadaihjparty.R
import com.example.wadaihjparty.SplashActivity
import com.example.wadaihjparty.data.manager.SessionManager
import com.example.wadaihjparty.databinding.FragmentProfileBinding
import com.example.wadaihjparty.ui.history.HistoryFragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // Inisialisasi ViewModel untuk Profile
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupClickListeners()
    }

    // Fungsi untuk mengamati perubahan data dari
    private fun observeViewModel() {
        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                binding.tvUserName.text = profile.name
                binding.tvUserPhone.text = profile.phone
            } else {
                binding.tvUserName.text = "Data Pengguna Tidak Ditemukan"
                binding.tvUserPhone.text = "-"
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnHistory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout & Reset")
            .setMessage("Apakah Anda yakin ingin logout? Ini juga akan mereset status onboarding untuk keperluan demo.")
            .setPositiveButton("Ya, Logout") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                SessionManager.clearAllSession(requireContext())
                val intent = Intent(requireContext(), SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}