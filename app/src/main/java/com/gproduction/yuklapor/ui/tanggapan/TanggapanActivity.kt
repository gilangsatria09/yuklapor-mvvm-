package com.gproduction.yuklapor.ui.tanggapan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.TanggapanModel
import com.gproduction.yuklapor.databinding.ActivityTanggapanMasyarakatBinding
import com.gproduction.yuklapor.tools.DATA_LAPORKAN
import com.gproduction.yuklapor.tools.toast
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TanggapanActivity : AppCompatActivity(),
    TanggapanInterface {

    private val viewModel:TanggapanViewModel by lazy {
        ViewModelProvider(this).get(TanggapanViewModel::class.java)
    }

    private lateinit var binding: ActivityTanggapanMasyarakatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tanggapan_masyarakat)

        initToolbar()
        viewModel.tanggapanInterface = this

        if (intent != null){
            val laporkanModel:LaporkanModel = intent.getParcelableExtra(DATA_LAPORKAN)!!
            binding.model = laporkanModel

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.fetchTanggapan(laporkanModel.idTanggapan!!)
            }
        }

    }

    private fun initToolbar() {
        toolbar.title = "Tanggapan"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun getDataTanggapan(data: LiveData<Resource<TanggapanModel>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                   binding.tanggapanmodel = it.data
                }
                Status.ERROR -> {
                    toast("${it.message}")
                }
            }
        })
    }
}
