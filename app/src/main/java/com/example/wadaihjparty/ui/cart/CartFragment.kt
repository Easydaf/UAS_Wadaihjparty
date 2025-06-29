package com.example.wadaihjparty.ui.cart
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels // <-- Pastikan import ini ada
import androidx.lifecycle.Observer       // <-- Pastikan import ini ada
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wadaihjparty.data.local.CartManager
import com.example.wadaihjparty.databinding.FragmentCartBinding
import com.example.wadaihjparty.domain.model.CartItem

class CartFragment : Fragment(), CartItemListener {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter
    override fun onResume() {
        super.onResume()
        viewModel.reloadData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.btnCheckout.setOnClickListener {
            val totalItems = CartManager.getTotalItemCount()
            val totalPrice = CartManager.calculateTotalPrice()

            if (totalItems > 0) {
                val bottomSheet = CheckoutBottomSheetDialog(totalItems, totalPrice)
                bottomSheet.show(parentFragmentManager, "CheckoutBottomSheetDialog")
            } else {
                Toast.makeText(requireContext(), "Keranjangmu kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList(), this)
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.cartItems.observe(viewLifecycleOwner, Observer { items ->
            cartAdapter.updateData(items)

            if (items.isEmpty()) {
                binding.recyclerViewCart.visibility = View.GONE
                binding.tvEmptyCart.visibility = View.VISIBLE
                binding.btnCheckout.visibility = View.INVISIBLE
            } else {
                binding.recyclerViewCart.visibility = View.VISIBLE
                binding.tvEmptyCart.visibility = View.GONE
                binding.btnCheckout.isEnabled = true
            }
        })

        viewModel.totalPrice.observe(viewLifecycleOwner, Observer { price ->
        })
    }

    override fun onIncreaseClicked(cartItem: CartItem) {
        viewModel.increaseQuantity(cartItem)
    }

    override fun onDecreaseClicked(cartItem: CartItem) {
        viewModel.decreaseQuantity(cartItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}