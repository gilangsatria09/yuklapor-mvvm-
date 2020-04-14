package com.gproduction.yuklapor.ui.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.FragmentProfileBinding
import com.gproduction.yuklapor.ui.home.HomeFragment

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileInterface {
    private val viewModel by lazy{
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding:FragmentProfileBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.viewmodel = viewModel
        viewModel.profileInterface = this

        return binding.root
    }

    companion object{
        fun newInstance() : ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCardProfileClicked() {

    }

    override fun onCardRegistrasiClicked() {
    }

}
