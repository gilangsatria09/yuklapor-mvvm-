package com.gproduction.yuklapor.ui.profile.registrasiadmin

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.databinding.ActivityRegistrasiAdminBinding
import com.gproduction.yuklapor.tools.CustomView.Companion.dialogCustom
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.activity_registrasi_admin.*
import kotlinx.android.synthetic.main.toolbar.*

class RegistrasiAdminActivity : AppCompatActivity(),RegistrasiAdminInterface {

    private val viewModel by lazy{
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    private val dialog by lazy {
        dialogCustom(this@RegistrasiAdminActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityRegistrasiAdminBinding = DataBindingUtil.setContentView(this,R.layout.activity_registrasi_admin)
        binding.viewmodel = viewModel
        viewModel.registrasiAdminInterface = this
        initSpinner()
        initToolbar()
    }

    private fun initSpinner(){
        val adapter = ArrayAdapter.createFromResource(this,R.array.role_registrasi,R.layout.custom_spinner_text)
        adapter.setDropDownViewResource(R.layout.custom_spinner_text)
        spinnerRole.adapter = adapter
        spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.role = position + 1
            }

        }
    }

    private fun initToolbar(){
        toolbar.title = "Registrasi"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onFailed(pesan: String) {
        toast(pesan)
    }

    override fun onDataChecked(data: LiveData<Resource<Boolean>>) {
        data.observe(this, Observer {
            when (it.status){
                Status.SUCCESS ->{
                    viewModel.onAllChecked()
                }
                Status.ERROR -> {
                    toast("${it.message}")
                }
                Status.LOADING -> {
                    dialog.show()
                }
                else -> {}
            }
        })
    }

    override fun onRegisterAuth(data: LiveData<Resource<AuthResult>>) {
        data.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    viewModel.registrasiToDatabase(it.data)
                    toast("Registrasi Sukses!")
                    onBackPressed()
                }
                Status.ERROR -> {
                    toast("${it.message}")
                    dialog.dismiss()
                }
                Status.ERRORTHROWABLE -> {
                    dialog.dismiss()
                    when (it.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            toast("Format Email Salah!")
                        }
                        is FirebaseAuthUserCollisionException -> {
                            toast("Email Sudah Digunakan!")
                        }
                        is FirebaseAuthWeakPasswordException -> {
                            toast("Password harus lebih dari 6!")
                        }
                        else -> toast("Gagal Register, Periksa Data Terlebih Dahulu!")
                    }
                }
                Status.LOADING -> {}
            }
        })
    }
}
