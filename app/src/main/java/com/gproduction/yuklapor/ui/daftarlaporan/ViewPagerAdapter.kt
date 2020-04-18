package com.gproduction.yuklapor.ui.daftarlaporan

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.DiprosesFragment
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.SelesaiFragment
import com.gproduction.yuklapor.ui.daftarlaporan.fragment.BelumDiprosesFragment

class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val pages = listOf(
        BelumDiprosesFragment(),
        DiprosesFragment(),
        SelesaiFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun saveState(): Parcelable? {
        return null
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {

    }

}