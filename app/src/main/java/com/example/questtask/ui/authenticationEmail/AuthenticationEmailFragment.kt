package com.example.questtask.ui.authenticationEmail

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
import com.example.questtask.databinding.FragmentAuthenticationEmailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationEmailFragment : Fragment() {
    private lateinit var mAuth : FirebaseAuth
    companion object {
        fun newInstance() = AuthenticationEmailFragment()
    }

    private lateinit var viewmodel: AuthenticationEmailViewModel
    private lateinit var binding: FragmentAuthenticationEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navBar: BottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.nav_view)
        navBar.isVisible = false

        viewmodel = ViewModelProvider(this).get(AuthenticationEmailViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_authentication_email,
            container,
            false
        )
        mAuth = Firebase.auth

        binding.btnRegister.setOnClickListener{
            if(!containsEmail()){
                Toast.makeText(this.context, "Du musst eine E-Mail angeben!", Toast.LENGTH_SHORT).show()
            } else if (!containsPasswords()){
                Toast.makeText(this.context, "Du musst ein Passwort angeben!", Toast.LENGTH_SHORT).show()
            } else if(!passwordsAreEqual()){
                Toast.makeText(this.context, "Deine Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show()
            } else if(containsEmail() && passwordsAreEqual() && containsPasswords()){
                var email = binding.leEmail.text.toString()
                var password = binding.pwField.text.toString()
                createAccountWithEmail(email, password)
            }
        }
        return binding.root
    }

    private fun createAccountWithEmail(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("Authentication", "createUserWithEmail:success")
                    Toast.makeText(this.context, "Erfolgreich registriert!", Toast.LENGTH_SHORT).show()
                    if(viewmodel.prefProvider.getContainsFlag()){
                        this.findNavController().navigate(AuthenticationEmailFragmentDirections.actionAuthenticationEmailFragmentToQuestFragment())
                    } else {
                        this.findNavController().navigate(AuthenticationEmailFragmentDirections.actionAuthenticationEmailFragmentToHelloFragment())
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("Authentication", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.context,"Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun containsEmail(): Boolean{
        return (
    binding.leEmail.text.toString().trim().isNotEmpty()
    || binding.leEmail.text.toString().trim().isNotBlank())
    }
    private fun containsPasswords() : Boolean{
        return (binding.pwField.text.toString().trim().isNotEmpty()
                || binding.pwField.text.toString().trim().isNotBlank()
                && binding.pwFieldRepeat.text.toString().trim().isNotEmpty()
                || binding.pwFieldRepeat.text.toString().trim().isNotBlank())
    }
    private fun passwordsAreEqual() : Boolean{
        return binding.pwField.text.toString() == binding.pwFieldRepeat.text.toString()
    }


}