package com.example.moviesdbapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import com.example.moviesdbapplication.databinding.FragmentMoviesBinding
import com.example.moviesdbapplication.ui.MovieState
import com.example.moviesdbapplication.ui.adapter.MovieListAdapter
import com.example.moviesdbapplication.ui.viewmodels.MoviesViewModel
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : MyBaseFragment() {

    private lateinit var _bindig: FragmentMoviesBinding
    private val bindig get() = _bindig

    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var adapter: MovieListAdapter

    var currentList = MovieState.POPULAR
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindig = FragmentMoviesBinding.inflate(inflater, container, false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startCategories()

        initRecyclerView()

        viewModel.getMovies(currentList).observe(viewLifecycleOwner) { result ->
            handleMoviesResult(result)
        }

        bindig.chipGroupFilters.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipPopular -> {
                    currentList = MovieState.POPULAR
                }

                R.id.chipTopRated -> {
                    currentList = MovieState.TOP
                }

                R.id.chipRecommended -> {
                    currentList = MovieState.RECOMMENDED
                }
            }
            viewModel.getMovies(currentList).observe(viewLifecycleOwner) { result ->
                handleMoviesResult(result)
            }
        }
    }

    private fun handleMoviesResult(result: Result<MovieRemoteResponse>?) {
        when (result) {
            is Result.Success -> {
                hideLoading()
                result.data.results?.let { movies ->
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapter.submitList(movies)
                    }
                }
            }

            is Result.Error -> {
                hideLoading()
                bindig.progressBar.visibility = View.GONE
                result.exception.message?.let { showError(it) }
            }

            else -> {
                showLoading()
            }
        }
    }

    fun initRecyclerView() {
        adapter = MovieListAdapter()
        bindig.recyclerViewMovies.adapter = adapter
        bindig.recyclerViewMovies.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}