package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.newsapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        registerToolbarWithNavigation()
        registerBottomNavigationWithNavigation()
    }

    private fun registerBottomNavigationWithNavigation() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        //ids of menu items of each fragment in bottom navigation
        val mainFragmentsIds = bottomNavigationView.menu.children.map { it.itemId }.toSet()
        val appBarConfiguration = AppBarConfiguration(mainFragmentsIds)

        setupWithNavController(bottomNavigationView, navController)
        setupActionBarWithNavController(
            this,
            navController,
            appBarConfiguration
        )
    }

    private fun registerToolbarWithNavigation() {
        val materialToolbar = findViewById<MaterialToolbar>(R.id.materialToolbar)
        setSupportActionBar(materialToolbar)
        setupWithNavController(materialToolbar, navController)
    }
}