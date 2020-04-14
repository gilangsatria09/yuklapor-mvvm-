package com.gproduction.yuklapor.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.ActivityAuthBinding
import com.gproduction.yuklapor.tools.toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.*
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status.*
import com.gproduction.yuklapor.data.model.UserModel
import com.gproduction.yuklapor.tools.CustomDialog
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.showDialog
import com.gproduction.yuklapor.ui.MainActivityAdmin
import com.gproduction.yuklapor.ui.MainActivityMasyarakat
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.bottom_sheet_register.*

class AuthActivity : AppCompatActivity(),
    AuthInterface {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var viewModel: AuthViewModel

    private val sharedPreferences by lazy {
        SharedPreferences(applicationContext)
    }

    private val dialog by lazy{
        CustomDialog(this@AuthActivity)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAuthBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_auth)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        //Binding a View Model
        binding.viewmodel = viewModel

        viewModel.authInterface = this
        viewModel.isLoggedIn()

        //Init a Bottom Sheet for Registration
        initBottomSheet(bottomSheet)

        //Make Top Right and Left of a Card is rounded
        cardBackground.background = getDrawable(R.drawable.bg_card_rounded)

    }

    override fun onStarted(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            when(sharedPreferences.getRole()){
                0 -> {
                    val intent = Intent(this@AuthActivity, MainActivityMasyarakat::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this@AuthActivity,MainActivityAdmin::class.java)
                    startActivity(intent)
                }
            }
        } else {
            toast("Anda Harus Login Terlebih Dahulu!")
        }
    }

    override fun onSuccess(loginResponse: LiveData<Resource<AuthResult>>) {

        loginResponse.observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    dialog.dismiss()
                    sharedPreferences.setUid(it.data?.user?.uid)
                    viewModel.getUserData(it.data?.user?.uid!!)
                }
                ERROR -> {
                    dialog.dismiss()
                    toast("${it.message}")
                }
                LOADING -> dialog.show()
                ERRORTHROWABLE -> TODO()
            }
        })

    }

    override fun onRegister(firebaseUser: LiveData<Resource<AuthResult>>) {
        firebaseUser.observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    dialog.dismiss()
                    viewModel.registerToDatabase(it.data)
                    toast("Registrasi Sukses!")
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

                    etNik.text = null
                    etNamaLengkap.text = null
                    etNoTelepon.text = null
                    etEmailBottom.text = null
                    etPasswordBottom.text = null

                }
                ERROR -> {
                    toast("${it.message}")
                    dialog.dismiss()
                }
                ERRORTHROWABLE -> {
                    showDialog().dismiss()
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
                LOADING -> dialog.dismiss()
            }
        })
    }

    override fun onFailed(pesan: String) {
        toast(pesan)

    }

    override fun buttomSheetState() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun onChecked(boolean: LiveData<Resource<Boolean>>) {
        boolean.observe(this, Observer {
            when (it.status){
                SUCCESS ->{
                    viewModel.onAllChecked()
                }
                ERROR -> {
                    toast("${it.message}")
                }
                ERRORTHROWABLE -> TODO()
                LOADING -> TODO()
            }
        })
    }


    override fun onGetUserData(data: LiveData<Resource<UserModel>>) {
        data.observe(this, Observer { resource ->
            when(resource.status){
                SUCCESS -> {
                    resource.data?.let {
                        sharedPreferences.setNik(it.nik)
                        sharedPreferences.setRole(it.role!!)
                        sharedPreferences.setNama(it.nama)
                        when(it.role){
                            0 -> {
                                showDialog().dismiss()
                                val intent = Intent(this@AuthActivity,MainActivityMasyarakat::class.java)
                                startActivity(intent)
                            }
                            1 -> {
                                showDialog().dismiss()
                                val intent = Intent(this@AuthActivity,MainActivityAdmin::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
                ERROR -> TODO()
                ERRORTHROWABLE -> TODO()
                LOADING -> TODO()
            }
        })
    }

    fun initBottomSheet(view: View) {
        bottomSheetBehavior = BottomSheetBehavior.from(view)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        linearRegistrasi.visibility = View.GONE
                        linearWelcome.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        if (linearRegistrasi.visibility == View.GONE) {
                            linearWelcome.visibility = View.VISIBLE
                        } else if (linearRegistrasi.visibility == View.VISIBLE) {
                            linearWelcome.visibility = View.GONE
                        }
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        linearRegistrasi.visibility = View.VISIBLE
                        linearWelcome.visibility = View.GONE
                    }
                }
            }
        })
    }

}
