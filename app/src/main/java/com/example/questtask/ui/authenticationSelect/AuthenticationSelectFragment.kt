package com.example.questtask.ui.authenticationSelect

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.questtask.R
import com.example.questtask.databinding.AuthenticationSelectFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationSelectFragment : Fragment() {
    private lateinit var viewmodel: AuthenticationSelectViewModel
    private lateinit var binding: AuthenticationSelectFragmentBinding
    private lateinit var mAuth: FirebaseAuth
    companion object {
        fun newInstance() = AuthenticationSelectFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(this).get(AuthenticationSelectViewModel::class.java)
        // Hide the App bar
        (activity as AppCompatActivity).supportActionBar?.hide()
        // Hide the BottomNavigation for this Fragment
        val navBar: BottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.authentication_select_fragment,
            container,
            false
        )
        mAuth = Firebase.auth

        binding.btnNavRegister.setOnClickListener{
            this.findNavController().navigate(
                AuthenticationSelectFragmentDirections
                    .actionAuthenticationSelectFragmentToAuthenticationEmailFragment())
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            login(email, password)
        }
        return binding.root
    }

    fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this.context, "Willkommen zurück!", Toast.LENGTH_SHORT).show()
                    Log.i("LOGIN", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    this.findNavController()
                        .navigate(AuthenticationSelectFragmentDirections
                            .actionAuthenticationSelectFragmentToQuestFragment())
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("LOGIN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this.context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}