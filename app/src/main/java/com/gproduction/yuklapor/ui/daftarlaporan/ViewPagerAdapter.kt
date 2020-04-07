package com.gproduction.yuklapor.ui.daftarlaporan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.DiprosesFragment
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.SelesaiFragment
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.SemuaLaporanFragment
import com.gproduction.yuklapor.ui.laporkan.LaporkanFragment
import com.gproduction.yuklapor.ui.profile.ProfileFragment

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val pages = listOf(
        SemuaLaporanFragment(),
        DiprosesFragment(),
        SelesaiFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}