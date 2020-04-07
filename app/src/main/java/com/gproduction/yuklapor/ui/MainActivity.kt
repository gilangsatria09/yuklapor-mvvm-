package com.gproduction.yuklapor.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.ui.home.HomeFragment
import com.gproduction.yuklapor.ui.laporkan.LaporkanFragment
import com.gproduction.yuklapor.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationClickListener)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)
    }

    private val onNavigationClickListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId){
            R.id.action_home -> {
                val homeFragment = HomeFragment.newInstance()
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