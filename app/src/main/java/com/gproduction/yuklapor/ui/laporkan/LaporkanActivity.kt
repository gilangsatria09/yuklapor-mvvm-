package com.gproduction.yuklapor.ui.laporkan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.tools.DATA_LAPORKAN
import kotlinx.android.synthetic.main.toolbar.*

class LaporkanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporkan)
        initToolbar()

        if (intent != null){
            val laporkanModel:LaporkanModel? = intent.getParcelableExtra(DATA_LAPORKAN)

            laporkanModel?.let {
                val fragment = LaporkanFragment.newInstance(it)
                addFragment(fragment)
            }
        }
    }

    private fun initToolbar(){
        toolbar.title = "Edit Laporan"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun addFragment(fragment: Fragment) : FragmentTransaction {
        return supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            replace(R.id.fragmentContent,fragment)
            commit()
        }
    }
}
