package com.gproduction.yuklapor.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.tools.CustomDialog
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.ui.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.bottom_sheet_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthActivity : AppCompatActivity(),
    AuthInterface {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var viewModel: AuthViewModel
    private lateinit var dialog: CustomDialog

    private val sharedPreferences by lazy {
        SharedPreferences(applicationContext)
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

        //Call a Custom Dialog Class
        dialog = CustomDialog(this@AuthActivity)

        //Make Top Right and Left of a Card is rounded
        cardBackground.background = getDrawable(R.drawable.bg_card_rounded)

    }

    override fun onStarted(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            toast("Anda Harus Login Terlebih Dahulu!")
        }
    }

    override fun onSuccess(loginResponse: LiveData<Resource<AuthResult>>) {

        loginResponse.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                    sharedPreferences.setUid(it.data?.user?.uid)

                    GlobalScope.launch {
                        getNIKBackground(it.data?.user?.uid.toString())
                    }

                    toast("Berhasil Login, ${it.data?.user?.uid}")
                }
                Status.ERROR -> toast("${it.message}")
                Status.LOADING -> TODO()
            }
        })

    }

    override fun onRegister(firebaseUser: LiveData<Resource<AuthResult>>) {
        firebaseUser.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.registerToDatabase(it.data)
                    toast("Registrasi Sukses!")
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

                    etNik.text = null
                    etNamaLengkap.text = null
                    etNoTelepon.text = null
                    etEmailBottom.text = null
                    etPasswordBottom.text = null


                    dialog.dismiss()
                }
                Status.ERROR -> {
                    toast("${it.message}")
                    dialog.dismiss()
                }
                Status.ERRORTHROWABLE -> {
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
                    dialog.dismiss()
                }
            }
        })
    }

    override fun onFailed(pesan: String) {
        toast(pesan)

    }

    override fun onLoading() {
        dialog.show()
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
                Status.SUCCESS ->{
                    viewModel.onAllChecked()
                }
                Status.ERROR -> {
                    toast("${it.message}")
                }
            }
        })
    }

    override fun onGetNik(string: LiveData<String>) {
        string.observe(this, Observer {
            sharedPreferences.setNik(it)
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

    //I'm Just testing it, but it works. But i don't know how to use it properly so..
    suspend fun getNIKBackground(uid:String) = withContext(Dispatchers.Main){
        viewModel.getNIK(uid)
    }


}
