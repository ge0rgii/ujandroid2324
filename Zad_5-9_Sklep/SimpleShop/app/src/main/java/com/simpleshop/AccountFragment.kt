package com.simpleshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.simpleshop.databinding.FragmentAccountBinding
import com.simpleshop.models.User
import com.simpleshop.viewModel.AccountViewModel
import retrofit2.Response

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindListeners()
        observeViewModel()
        setupUI()
    }

    // bind listeners to buttons
    private fun bindListeners() {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.loginUser(email, password)
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.registerUser(email, password)
        }

        binding.btnSaveChanges.setOnClickListener {
            val email = viewModel.getLoggedInUser()?.email ?: binding.etProfileEmail.text.toString()
            // catch old password from view model
            val newPassword = binding.etNewPassword.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val address = binding.etAddress.text.toString()

            viewModel.updateUserProfile(email, newPassword, firstName, lastName, address)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
            showLoginSignUpForm()
            clearProfileDetailsForm()
            clearLoginForm()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner) { response ->
            handleUserResponse(response)
        }
    }

    private fun setupUI() {
        val user = viewModel.getLoggedInUser()
        if (user != null) {
            showProfileDetailsForm(user)
        } else {
            showLoginSignUpForm()
        }

    }

    private fun showProfileDetailsForm(user: User) {
        binding.loginSignupForm.visibility = View.GONE
        binding.profileDetailsForm.visibility = View.VISIBLE
        binding.etProfileEmail.setText(user.email)
        binding.etFirstName.setText(user.firstName)
        binding.etLastName.setText(user.lastName)
        binding.etAddress.setText(user.address)
    }

    private fun clearProfileDetailsForm() {
        binding.etProfileEmail.text = null
        binding.etFirstName.text = null
        binding.etLastName.text = null
        binding.etAddress.text = null
        binding.etNewPassword.text = null
    }

    // clear login form fields
    private fun clearLoginForm() {
        binding.etEmail.text = null
        binding.etPassword.text = null
    }

    private fun showLoginSignUpForm() {
        binding.loginSignupForm.visibility = View.VISIBLE
        binding.profileDetailsForm.visibility = View.GONE
    }

    private fun handleUserResponse(response: Response<User>) {
        if (response.isSuccessful && response.body() != null) {
            Toast.makeText(context, "Operation successful", Toast.LENGTH_SHORT).show()
            val user = response.body()!!
            viewModel.saveLoggedInUser(user)
            showProfileDetailsForm(user)
        } else {
            Toast.makeText(context, "Operation failed: ${response.message()}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
