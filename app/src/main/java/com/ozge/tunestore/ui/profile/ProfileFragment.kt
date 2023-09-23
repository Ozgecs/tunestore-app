package com.ozge.tunestore.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val name = FirebaseAuth.getInstance()
        val user = name.currentUser
        val email = user?.email

        with(binding) {

            tvemail.text = email

            btnout.setOnClickListener{
                auth.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_signUpFragment)
            }
        }
    }
}