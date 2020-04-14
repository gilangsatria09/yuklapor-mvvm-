package com.gproduction.yuklapor.ui.detaillaporan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status.*
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.TanggapanModel
import com.gproduction.yuklapor.databinding.ActivityDetailLaporanBinding
import com.gproduction.yuklapor.tools.*
import com.gproduction.yuklapor.ui.laporkan.LaporkanActivity
import com.gproduction.yuklapor.ui.tanggapan.TanggapanActivity
import kotlinx.android.synthetic.main.activity_detail_laporan.*
import kotlinx.android.synthetic.main.layout_custom_alert_dialog.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailLaporanActivity : AppCompatActivity(), DetailLaporanInterface {

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailLaporanViewModel::class.java)
    }

    private val customAlertDialog by lazy {
        CustomAlertDialog(this@DetailLaporanActivity)
    }

    private val dialog by lazy{
        CustomDialog(this@DetailLaporanActivity)
    }

    private val sharedPreferences by lazy {
        SharedPreferences(this@DetailLaporanActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailLaporanBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_detail_laporan)

        initToolbar()
        setSpinner()

        viewModel.detailLaporanInterface = this
        binding.viewmodel = viewModel

        intent.extras?.let {
            val laporkanModel: LaporkanModel? = it.getParcelable(DATA_LAPORKAN)
            binding.model = laporkanModel
            initDetailData(laporkanModel)

            when (sharedPreferences.getRole()) {
                0 -> {
                    setToolbarMenu(laporkanModel)
                }
                else -> {
                    frameStatus.visibility = View.VISIBLE
                    buttonUbahStatus.visibility = View.VISIBLE

                    viewModel.namaPegawai = sharedPreferences.getNama()
                    laporkanModel!!.idTanggapan?.let { id ->
                        viewModel.getDetailTanggapan(id)
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        toolbar.title = "Detail Laporan"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initDetailData(laporkanModel: LaporkanModel?){
        laporkanModel?.let {
            spinnerStatus.setSelection(it.status!!)
        }
    }

    private fun setToolbarMenu(data: LaporkanModel?) {
        data?.let {
            if (it.status == 0) {
                toolbar.inflateMenu(R.menu.menu_toolbar)

                toolbar.setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.editMenu -> {
                            val intent =
                                Intent(this@DetailLaporanActivity, LaporkanActivity::class.java)
                            intent.apply {
                                putExtra(DATA_LAPORKAN, it)
                            }
                            startActivity(intent)
                            true
                        }
                        R.id.deleteMenu -> {
                            customAlertDialog.show()
                            customAlertDialog.btnYa.setOnClickListener { _ ->
                                viewModel.deleteLaporan(it)
                                customAlertDialog.dismiss()
                            }
                            customAlertDialog.btnTidak.setOnClickListener {
                                customAlertDialog.dismiss()
                            }
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun setSpinner() {

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.statusSpinner = position

                if (position == 2){
                    etTanggapan.visibility = View.VISIBLE
                } else{
                    etTanggapan.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    override fun onButtonTanggapanClicked(data: LaporkanModel) {
        val intent = Intent(this@DetailLaporanActivity, TanggapanActivity::class.java)
        intent.apply {
            putExtra(DATA_LAPORKAN, data)
        }
        startActivity(intent)
    }

    override fun onDeleteLaporan(data: LiveData<Resource<String>>) {
        data.observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    toast("${it.data}")
                    onBackPressed()
                    finish()
                }
                ERROR -> {
                    toast("${it.message}")
                }
            }
        })
    }

    override fun onButtonStatusClickedFailed() {
        toast("Pilih Status Terlebih Dahulu!")
    }

    override fun onButtonStatusClicedSuccess(data: LiveData<Resource<Boolean>>) {
        data.observe(this, Observer {
            when(it.status){
                SUCCESS -> {
                    dialog.dismiss()
                    toast("Status Berhasil Diubah!")
                    onBackPressed()
                    finish()
                }
                ERROR -> {
                    dialog.dismiss()
                    toast("${it.message}")
                }
                LOADING -> dialog.show()
            }
        })
    }

    override fun onGetDetailTanggapan(data: LiveData<Resource<TanggapanModel>>) {
        data.observe(this, Observer {
            when(it.status){
                SUCCESS -> {
                    viewModel.tanggapanModel = it.data
                    etTanggapan.setText("${it.data?.tanggapan}")
                    Log.d("DATAA","$it")
                }
                ERROR -> toast("${it.message}")
                else -> TODO()
            }
        })
    }
}
