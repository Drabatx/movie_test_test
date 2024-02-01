package com.example.moviesdbapplication.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesdbapplication.databinding.FragmentGalleryBinding
import com.example.moviesdbapplication.domain.model.GenericResponse
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.ui.adapter.GalleryAdapter
import com.example.moviesdbapplication.ui.viewmodels.GalleryViewModel
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : MyBaseFragment() {
    private lateinit var _binding: FragmentGalleryBinding
    private val binding get() = _binding!!

    private val viewModel by viewModels<GalleryViewModel>()

    private lateinit var adapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        binding.btnAddGalley.setOnClickListener { view ->
            requestGalleryPermission()
        }
        viewModel.getAllImages().observe(viewLifecycleOwner) { result ->
            handleMoviesResult(result)

        }
    }

    fun initRecyclerView() {
        adapter = GalleryAdapter(requireContext(), listOf())
        binding.recyclerViewGallery.adapter = adapter
        binding.recyclerViewGallery.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun handleMoviesResult(result: Result<List<ImageResponse>>?) {
        when (result) {
            is Result.Success -> {
                hideLoading()
                result.data.let { images ->
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapter.updateImages(images)
                    }
                }
            }

            is Result.Error -> {
                hideLoading()
                result.exception.message?.let { showError(it) }
            }

            else -> {
                showLoading()
            }
        }
    }

    private fun handleResponseUploadImage(result: Result<GenericResponse>) {
        when (result) {
            is Result.Success -> {
                hideLoading()
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

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultCameraLauncher.launch(intent)
    }

    private fun requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        resultGalleryLauncher.launch(intent)
    }

    private var resultGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val clipData = result.data?.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val imageUri: Uri = clipData.getItemAt(i).uri
                        viewModel.saveImage(imageUri).observe(viewLifecycleOwner) { response ->
                            handleResponseUploadImage(response)
                        }
                        Log.i("TAG", ": ${imageUri}")
                    }
                } else {
                    val imageUri: Uri = result.data?.data ?: return@registerForActivityResult
                    Log.i("TAG", ": ${imageUri}")
                }
            }

        }
    private var resultCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoUri = result.data?.data
                Log.i("TAG", ": ${photoUri}")
            }
        }

    private var requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }

    private var requestGalleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                requestGalleryPermission()
            }
        }
}