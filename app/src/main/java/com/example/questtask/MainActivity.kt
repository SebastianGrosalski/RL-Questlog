package com.example.questtask

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.questtask.repository.PreferenceProvider
import com.example.questtask.repository.firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseRepo : FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.questFragment, R.id.doneQuestsFragment, R.id.progressFragment, R.id.friendListFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // Set up different fragments as entry point for the app
        // depending on whether the shared preferences contain something
        val navHost : NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navInflater : NavInflater = navController.navInflater
        val graph : NavGraph = navInflater.inflate(R.navigation.mobile_navigation)

        val prefProvider = PreferenceProvider(applicationContext)
        mAuth = Firebase.auth
        firebaseRepo = FirebaseRepository

        if(!prefProvider.getContainsFlag()){
            graph.startDestination = R.id.helloFragment
        } else {
            graph.startDestination = R.id.questFragment
        }
        if(mAuth.currentUser == null){
            graph.startDestination = R.id.authenticationSelectFragment
        }

        navController.graph = graph
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.preference_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.action_preferences -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_settingsFragment)
            R.id.action_logout -> {
                Firebase.auth.signOut()
                Toast.makeText(this.applicationContext, "Ausgeloggt.", Toast.LENGTH_SHORT).show()
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_authenticationSelectFragment)
            }
            R.id.action_addFriend -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_addFriendFragment)
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
