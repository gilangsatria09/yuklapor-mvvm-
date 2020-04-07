package com.gproduction.yuklapor.ui.daftarlaporan.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout

import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.ui.daftarlaporan.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_daftar_laporan.*

/**
 * A simple [Fragment] subclass.
 */
class DaftarLaporanFragment(private var toolbar: Toolbar) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_daftar_laporan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        setTabIcon()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tabLayout.selectedTabPosition) {
                    0 -> toolbar.title = "Semua Laporan"
                    1 -> toolbar.title = "Diproses"
                    2 -> toolbar.title = "Selesai"
                }
            }

        })
    }

    private fun setTabIcon() {
        tabLayout.getTabAt(0)?.icon = requireActivity().getDrawable(R.drawable.ic_note)
        tabLayout.getTabAt(1)?.icon = requireActivity().getDrawable(R.drawable.proses)
        tabLayout.getTabAt(2)?.icon = requireActivity().getDrawable(R.drawable.ic_check)
    }

    companion object{
        fun newInstance(toolbar: Toolbar) : DaftarLaporanFragment {
            val fragment =
                DaftarLaporanFragment(
                    toolbar
                )
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
