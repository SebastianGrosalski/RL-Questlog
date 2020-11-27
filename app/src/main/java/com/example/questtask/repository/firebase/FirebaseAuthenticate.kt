package com.example.questtask.repository.firebase

import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.questtask.ui.authenticationEmail.AuthenticationEmailFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthenticate{
    val mAuth = Firebase.auth
    val firebaseRepo = FirebaseRepository
    fun createAccountWithEmail(email: String, password: String, name: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                var userName = name
                firebaseRepo.addUser(userName)
                Log.i("AUTHENTICATE", "Nutzer mit namen $userName erstellt.")
            } else {
                Log.i("AUTHENTICATE", "FAIL")
            }
        }
    }
}