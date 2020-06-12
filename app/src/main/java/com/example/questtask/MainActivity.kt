package com.example.questtask

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.ui.home.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set up different fragments as entry point for the app
        // depending on whether the shared preferences contain something
        val navHost : NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navInflater : NavInflater = navController.navInflater
        val graph : NavGraph = navInflater.inflate(R.navigation.mobile_navigation)

        val prefProvider = PreferenceProvider(applicationContext)

        if(!prefProvider.getContainsFlag()){
            graph.startDestination = R.id.helloFragment
        } else {
            graph.startDestination = R.id.navigation_home
        }
        navController.graph = graph
    }
}
