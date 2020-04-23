package com.gproduction.yuklapor.ui.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.ui.home.masyarakat.HomeMasyarakatFragment
import com.gproduction.yuklapor.ui.laporkan.LaporkanFragment
import com.gproduction.yuklapor.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_home_masyarakat.*

class HomeActivityMasyarakat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_masyarakat)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationClickListener)
        val fragment = HomeMasyarakatFragment.newInstance()
        addFragment(fragment)
    }

    private val onNavigationClickListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId){
            R.id.action_home -> {
                val homeFragment = HomeMasyarakatFragment.newInstance()
                addFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_laporkan -> {
                val laporkanFragment = LaporkanFragment.newInstance()
                addFragment(laporkanFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                val profileFragment = ProfileFragment.newInstance()
                addFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            .replace(R.id.fragmentContent,fragment)
            .commit()
    }
}
