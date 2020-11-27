package com.example.questtask.ui.authenticationSelect

import android.app.Activity
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
import com.example.questtask.repository.firebase.FirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationSelectFragment : Fragment() {
    private lateinit var viewmodel: AuthenticationSelectViewModel
    private lateinit var binding: AuthenticationSelectFragmentBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseRepo : FirebaseRepository
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
        firebaseRepo = FirebaseRepository
        binding.btnNavRegister.setOnClickListener{
            this.findNavController().navigate(
                AuthenticationSelectFragmentDirections
                    .actionAuthenticationSelectFragmentToAuthenticationEmailFragment())
        }
        binding.btnLogin.setOnClickListener{
            if(!containsEmail()){
                Toast.makeText(this.context, "Bitte gib eine E-Mail ein!", Toast.LENGTH_SHORT).show()
            } else if (!containsPassword()){
                Toast.makeText(this.context, "Bitte gib ein Passwort ein!", Toast.LENGTH_SHORT).show()
            } else if(containsPassword() && containsEmail()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                login(email, password)
            }
        }
        return binding.root
    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this.context, "Willkommen zurück!", Toast.LENGTH_SHORT).show()
                    Log.i("LOGIN", "signInWithEmail:success")
                    firebaseRepo.getCurrentUser().addOnSuccessListener{ user ->
                       viewmodel.prefProvider.putName(user.get("name").toString())
                        Log.i("FIRESTOREREPOSITORY", "Currentusername: ${FirebaseRepository.username}")
                        this.findNavController().navigate(AuthenticationSelectFragmentDirections.actionAuthenticationSelectFragmentToQuestFragment())
                    }
                        .addOnFailureListener {
                            viewmodel.prefProvider.putName("Fail")
                            Log.i("FIRESTOREREPOSITORY", "GetCurrentUserName Failure")
                        }
                    } else {
                    // If sign in fails, display a message to the user.
                    Log.i("LOGIN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this.context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun containsEmail(): Boolean{
        return (binding.etEmail.text.toString().isNotEmpty() ||
                binding.etEmail.text.toString().isNotBlank())
    }
    fun containsPassword(): Boolean{
        return (binding.etPassword.text.toString().isNotEmpty() ||
                binding.etPassword.text.toString().isNotBlank())
    }
}