package com.gproduction.yuklapor.ui.daftarlaporan.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.ActivityDaftarLaporanBinding
import com.gproduction.yuklapor.ui.daftarlaporan.DaftarLaporanViewModel
import kotlinx.android.synthetic.main.toolbar.*

class DaftarLaporanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityDaftarLaporanBinding = DataBindingUtil.setContentView(this,R.layout.activity_daftar_laporan)
        val viewModel = ViewModelProvider(this).get(DaftarLaporanViewModel::class.java)

        binding.viewmodel = viewModel

        initToolbar()
        val fragment = DaftarLaporanFragment.newInstance(toolbar)
        addFragment(fragment)
    }

    private fun initToolbar(){
        toolbar.title = "Semua Laporan"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .replace(R.id.fragmentContent,fragment)
            .commit()
    }




}
