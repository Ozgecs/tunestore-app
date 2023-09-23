package com.ozge.tunestore.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.isPasswordValid
import com.ozge.tunestore.common.isValidEmail
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 13.09.2023
 * @author Özge Şahin
 */
@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let {
            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        }

        with(binding) {

            btnsignup.setOnClickListener {
                if (check(editTextEmailAddress,editTextPassword)){

                    signUp(editTextEmailAddress.text.toString(),editTextPassword.text.toString())
                }
                else{
                    Toast.makeText(context,"Kayıt İşlemi Başarısız",Toast.LENGTH_SHORT).show()
                }
            }

            tvlogin.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }

        }

    }

    private fun signUp(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            Toast.makeText(context,"Kayıt İşlemi Başarılı",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        }.addOnFailureListener {
            Toast.makeText(context,"Bir hata meydana geldi",Toast.LENGTH_SHORT).show()
        }
    }

    private fun check(email: EditText, password: EditText): Boolean{
        val check = when{
            email.isValidEmail() -> true
            password.isPasswordValid() ->true
            else -> false
        }
        return check
    }
}