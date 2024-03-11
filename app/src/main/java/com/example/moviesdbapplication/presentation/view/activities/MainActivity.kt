package com.example.moviesdbapplication.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_movies)

        setupNavController()

        binding.myBottomNavigation.selectedItemId = R.id.menu_movies
    }

    override fun onStart() {
        super.onStart()
        setTitleActionBar()
    }
    private fun setTitleActionBar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_profile_fragment -> {
                    supportActionBar?.title = getString(R.string.title_profile)
                }
                R.id.nav_movies_fragment -> {
                    supportActionBar?.title = getString(R.string.title_movies)
                }
                R.id.nav_map_fragment -> {
                    supportActionBar?.title = getString(R.string.title_map)
                }
                R.id.nav_gallery_fragment -> {
                    supportActionBar?.title = getString(R.string.title_gallery)
                }
                // Agrega más casos según sea necesario
            }
        }

    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment?
                ?: NavHostFragment.create(R.navigation.nav_graph)
        navController = navHostFragment.navController

        binding.myBottomNavigation.setupWithNavController(navController)

        binding.myBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_profile -> {
                    navController.navigate(R.id.nav_profile_fragment)
                }

                R.id.menu_movies -> {
                    navController.navigate(R.id.nav_movies_fragment)
                }

                R.id.menu_map -> {
                    navController.navigate(R.id.nav_map_fragment)
                }

                R.id.menu_gallery -> {
                    navController.navigate(R.id.nav_gallery_fragment)
                }
            }
            true
        }

    }
}