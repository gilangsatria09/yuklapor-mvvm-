package com.gproduction.yuklapor.ui.profile.detailprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.UserModel
import com.gproduction.yuklapor.databinding.ActivityDetailProfileBinding
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.toolbar.*

class DetailProfileActivity : AppCompatActivity(),DetailProfileInterface {

    private val viewModel by lazy{
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }
    private val sharedPreferences by lazy {
        SharedPreferences(this@DetailProfileActivity)
    }

    private lateinit var binding:ActivityDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_profile)
        initToolbar()

        viewModel.detailProfileInterface = this
        viewModel.getDataUser(sharedPreferences.getUid()!!)
    }

    private fun initToolbar() {
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onGetData(data: LiveData<Resource<UserModel>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> binding.model = it.data
                Status.ERROR -> toast("Gagal")
                else ->{

                }
            }
        })
    }
}
