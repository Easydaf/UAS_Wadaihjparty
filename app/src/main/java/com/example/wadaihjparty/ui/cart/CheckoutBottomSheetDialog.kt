package com.example.wadaihjparty.ui.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.wadaihjparty.data.local.CartManager
import com.example.wadaihjparty.data.manager.NetworkUtils
import com.example.wadaihjparty.databinding.FragmentCheckoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.Locale

class CheckoutBottomSheetDialog(private val totalItems: Int, private val totalPrice: Double) : BottomSheetDialogFragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTotalItems.text = "$totalItems Items"
        binding.tvTotalPrice.text = formatPrice(totalPrice)

        binding.btnWhatsapp.setOnClickListener {
            if (NetworkUtils.isInternetAvailable(requireContext())) {
                sendOrderToWhatsApp()
            } else {
                // Jika tidak ada internet, tampilkan pesan dan jangan lakukan apa-apa
                Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun formatPrice(price: Double): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(price)
    }

    private fun sendOrderToWhatsApp() {
        val cartItems = CartManager.getCartItems()
        if (cartItems.isEmpty()) {
            Toast.makeText(requireContext(), "Keranjang sudah dipesan!", Toast.LENGTH_SHORT).show()
            dismiss()
            return
        }

        val messageBuilder = StringBuilder()
        messageBuilder.append("Halo, saya mau pesan kue:\n\n")
        cartItems.forEach { item ->
            messageBuilder.append("🍰 *${item.cake.name}* (x${item.quantity})\n")
            if (item.cake.name.contains("Custom")) {
                messageBuilder.append("   - Detail: ${item.cake.description}\n")
            }
        }
        messageBuilder.append("\n--------------------\n")
        messageBuilder.append("Total Pesanan: *${formatPrice(totalPrice)}*")
        messageBuilder.append("\n\nTerima kasih.")

        val orderMessage = messageBuilder.toString()

        viewModel.saveOrderToHistory(orderMessage, totalPrice)
        CartManager.clearCart()

        try {
            val phoneNumber = "+62895366312331"
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(orderMessage)}"
            intent.data = Uri.parse(url)
            startActivity(intent)
            dismiss()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "WhatsApp tidak terinstall.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}