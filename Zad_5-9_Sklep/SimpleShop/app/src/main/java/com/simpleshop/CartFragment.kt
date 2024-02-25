package com.simpleshop

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.simpleshop.adapters.ProductCartAdapter
import com.simpleshop.databinding.FragmentCartBinding
import com.simpleshop.utils.SharedPreferencesHelper
import com.simpleshop.viewModel.ProductDetailsViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var productCartAdapter: ProductCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(ProductDetailsViewModel::class.java)

        // Initialize Adapter
        productCartAdapter = ProductCartAdapter(viewModel.repository)

        // Setup RecyclerView
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productCartAdapter
        }

        // Observe Cart Items
        viewModel.allProductsInCart.observe(viewLifecycleOwner) { products ->
            productCartAdapter.setProductsList(products)
            updateUiBasedOnCartContents(products.isEmpty())
        }

        binding.btnProceedToCheckout.setOnClickListener {
            if (isLoggedIn()) {
                submitOrder()
            } else {
                promptLogin()
            }
        }
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        return sharedPreferencesHelper.getLoggedInUser() != null
    }

    private fun submitOrder() {
        viewModel.submitOrder { success ->
            if (success) {
                showToast("Order placed successfully")
            } else {
                showToast("Failed to place order")
            }
        }
    }

    private fun promptLogin() {
        AlertDialog.Builder(requireContext())
            .setTitle("Musisz być zalogowany")
            .setMessage("Musisz być zalogowany, aby kontynuować")
            .setPositiveButton("Zaloguj") { _, _ ->
                findNavController().navigate(R.id.action_cartFragment_to_accountFragment)
            }
            .setNegativeButton("Cofnij", null)
            .show()
    }

    private fun updateUiBasedOnCartContents(isEmpty: Boolean) {
        binding.tvCartEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.cartRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
