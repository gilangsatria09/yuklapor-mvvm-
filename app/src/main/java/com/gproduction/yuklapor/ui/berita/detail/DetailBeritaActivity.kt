package com.gproduction.yuklapor.ui.berita.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.BeritaModel
import com.gproduction.yuklapor.databinding.ActivityDetailBeritaBinding
import com.gproduction.yuklapor.tools.DATA_BERITA
import kotlinx.android.synthetic.main.toolbar.*

class DetailBeritaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityDetailBeritaBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_berita)

        initToolbar()

        if (intent != null){
            val beritaModel:BeritaModel? = intent.getParcelableExtra(DATA_BERITA)
            beritaModel?.let {
                binding.model = it
            }
        }

    }

    private fun initToolbar() {
        toolbar.title = "Detail Berita"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
