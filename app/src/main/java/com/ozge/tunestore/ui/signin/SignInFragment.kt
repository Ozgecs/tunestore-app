package com.ozge.tunestore.ui.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 13.09.2023
 * @author Özge Şahin
 */
@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        with(binding) {
            btnlogin.setOnClickListener {
                val email = editTextEmailAddress.text.toString()
                val password = editTextPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()){
                    signIn(email, password)
                }else{
                    Toast.makeText(context,"Boş bırakılamaz!",Toast.LENGTH_SHORT).show()
                }
            }

            tvregister.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    private fun signIn(email: String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }.addOnFailureListener {
            Toast.makeText(context,"Yanlış e-posta veya şifre!", Toast.LENGTH_SHORT).show()
        }
    }
}