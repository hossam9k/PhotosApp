package com.poc.photosapp.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.poc.photosapp.di.NetworkConnectivityException
import com.poc.photosapp.utilities.Status
import com.poc.photosapp.utilities.show
import com.poc.photosapp.utilities.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.poc.photosapp.core.entities.PhotoItem
import com.poc.photosapp.databinding.FragmentHomeBinding
import com.poc.photosapp.presentation.ui.adapters.PhotosAdapter
import com.poc.photosapp.presentation.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), PhotosAdapter.OnItemClicked {

    private lateinit var binding: FragmentHomeBinding
    private val photosViewModel by viewModels<PhotosViewModel>()
    private lateinit var navController: NavController
    private lateinit var adapter: PhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        navController = findNavController()

        initRecyclerView()

        initPhotosObserver()

        binding.retry = photosViewModel.retry()

        binding.shimmerLayout.startShimmer()

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = PhotosAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    private fun initPhotosObserver() {
        lifecycleScope.launch {
            photosViewModel.photos.collect { resource ->
                binding.resource = resource
                when (resource.status) {
                    Status.LOADING -> {
                        binding.layoutApiError.clContainer.show(false)
                        binding.layoutNoInternet.clContainer.show(false)
                    }

                    Status.SUCCESS -> {
                        binding.layoutApiError.clContainer.show(false)
                        binding.layoutNoInternet.clContainer.show(false)
                        resource.response?.let {
                            adapter.submitList(it)
                        }
                    }

                    Status.ERROR -> {
                        var messageError: String = ""
                        if (resource.error is NetworkConnectivityException) {
                            messageError = resource.error.errorMessage
                            binding.layoutNoInternet.clContainer.show()
                            binding.layoutApiError.clContainer.show(false)
                        } else {
                            messageError = resource.error?.message ?: "General Error message"
                            binding.layoutApiError.clContainer.show()
                            binding.layoutNoInternet.clContainer.show(false)
                        }

                        showSnackBar(
                            message = messageError,
                            duration = Snackbar.LENGTH_LONG
                        )
                    }



                }
            }
        }

    }

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onItemClicked(photo: PhotoItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(photo)
        navController.navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerLayout.startShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.shimmerLayout.stopShimmer()
    }
}