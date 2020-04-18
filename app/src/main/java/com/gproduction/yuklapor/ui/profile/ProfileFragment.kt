package com.gproduction.yuklapor.ui.profile


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.FragmentProfileBinding
import com.gproduction.yuklapor.tools.CustomAlertDialog
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.ui.auth.AuthActivity
import com.gproduction.yuklapor.ui.profile.detailprofile.DetailProfileActivity
import com.gproduction.yuklapor.ui.profile.registrasiadmin.RegistrasiAdminActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_custom_alert_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileInterface {
    private val viewModel by lazy{
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    private val customAlertDialog by lazy {
        CustomAlertDialog(requireContext())
    }

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sharedPreferences.getRole() == 1){
            cardTambahAdmin.visibility = View.VISIBLE
        }else{
            cardTambahAdmin.visibility = View.GONE
        }
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
        val intent = Intent(requireContext(),DetailProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onCardRegistrasiClicked() {
        val intent = Intent(requireContext(),RegistrasiAdminActivity::class.java)
        startActivity(intent)
    }

    override fun onButtonLogout() {
        customAlertDialog.show()
        customAlertDialog.btnYa.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(),AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()

        }
        customAlertDialog.btnTidak.setOnClickListener {
            customAlertDialog.dismiss()
        }
    }
}
