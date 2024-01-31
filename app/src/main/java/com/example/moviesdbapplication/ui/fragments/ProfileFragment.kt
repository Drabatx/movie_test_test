package com.example.moviesdbapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.databinding.FragmentProfileBinding
import com.example.moviesdbapplication.domain.model.UserInfo
import com.example.moviesdbapplication.ui.viewmodels.ProfileViewModel
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : MyBaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGuardarDatos.setOnClickListener {
            val userInfo = UserInfo(
                binding.inputTextFullName.editText?.text?.toString() ?: "",
                binding.inputTextEmail.editText?.text?.toString() ?: "",
                binding.inputTextPhone.editText?.text?.toString() ?: ""
            )
            if (userInfo.isValidate()) {
                viewModel.saveUser(userInfo).observe(viewLifecycleOwner) { result ->
                    handleResponseUserInfo(result)
                }
            } else {
                showError(getString(R.string.invalidate_fields))
            }
        }
        viewModel.getUserInfo().observe(viewLifecycleOwner) { result ->
            handleResponseUserInfo(result)
        }

    }

    private fun handleResponseUserInfo(result: Result<UserInfo>) {
        when (result) {
            is Result.Success -> {
                hideLoading()
                binding.inputTextFullName.editText?.setText(result.data.nombre)
                binding.inputTextEmail.editText?.setText(result.data.email)
                binding.inputTextPhone.editText?.setText(result.data.telefono)
            }

            is Result.Error -> {
                hideLoading()
                result.exception.message?.let { showError(it) }
            }

            is Result.Loading -> {
                showLoading()
            }

        }
    }

}