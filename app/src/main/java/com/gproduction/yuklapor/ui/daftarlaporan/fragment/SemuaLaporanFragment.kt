package com.gproduction.yuklapor.ui.daftarlaporan.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.databinding.FragmentSemuaLaporanBinding
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.daftarlaporan.DaftarLaporanInterface
import com.gproduction.yuklapor.ui.daftarlaporan.DaftarLaporanViewModel
import com.gproduction.yuklapor.ui.daftarlaporan.adapter.SemuaLaporanRVAdapter
import kotlinx.android.synthetic.main.fragment_semua_laporan.*

/**
 * A simple [Fragment] subclass.
 */
class SemuaLaporanFragment : Fragment(),DaftarLaporanInterface {
    private lateinit var viewModel:DaftarLaporanViewModel

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentSemuaLaporanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_semua_laporan,container,false)
        viewModel = ViewModelProvider(this).get(DaftarLaporanViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.daftarLaporanInterface = this

        sharedPreferences.getUid()?.let {
            viewModel.getAllData(it)
        }

        return binding.root
    }


    override fun onSuccess(data: LiveData<Resource<ArrayList<LaporkanModel>>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    setRecyclerView(it.data!!)
                }
                Status.ERROR ->{
                    requireContext().toast(it.message!!)
                }
            }
        })
    }

    private fun setRecyclerView(list:ArrayList<LaporkanModel>){
        rvSemuaLaporan.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SemuaLaporanRVAdapter(list,viewModel)
        }
    }

}
