package com.gproduction.yuklapor.ui.daftarlaporan.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.ActivityDaftarLaporanBinding
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.daftarlaporan.DaftarLaporanViewModel
import com.gproduction.yuklapor.ui.laporkan.LaporkanFragment
import kotlinx.android.synthetic.main.activity_daftar_laporan.*
import kotlinx.android.synthetic.main.toolbar.*

class DaftarLaporanActivity : AppCompatActivity(),InterfaceListener {
    private val sharedPreferences by lazy{
        SharedPreferences(this@DaftarLaporanActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityDaftarLaporanBinding = DataBindingUtil.setContentView(this,R.layout.activity_daftar_laporan)
        val viewModel = ViewModelProvider(this).get(DaftarLaporanViewModel::class.java)

        binding.viewmodel = viewModel
        viewModel.interfaceLaporan = this

        initToolbar()
        val fragment = DaftarLaporanFragment.newInstance(toolbar)
        addFragment(fragment)

        if (sharedPreferences.getRole() == 0){
            floatingButton.show()
        }else{
            floatingButton.hide()
        }
    }

    private fun initToolbar(){
        toolbar.title = "Semua Laporan"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment) : FragmentTransaction{
        return supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            replace(R.id.fragmentContent,fragment)
            commit()
        }
    }

    override fun onFloatingClicked() {
        val fragment = LaporkanFragment()
        floatingButton.hide()
        addFragment(fragment).addToBackStack(null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!floatingButton.isShown){
            floatingButton.show()
        }
    }
}
