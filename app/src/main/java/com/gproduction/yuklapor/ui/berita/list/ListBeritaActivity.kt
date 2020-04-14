package com.gproduction.yuklapor.ui.berita.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.BeritaModel
import com.gproduction.yuklapor.databinding.ActivityListBeritaBinding
import com.gproduction.yuklapor.tools.DATA_BERITA
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.berita.BeritaViewModel
import com.gproduction.yuklapor.ui.berita.adapter.DaftarBeritaRVAdapter
import com.gproduction.yuklapor.ui.berita.detail.DetailBeritaActivity
import kotlinx.android.synthetic.main.activity_list_berita.*
import kotlinx.android.synthetic.main.toolbar.*

class ListBeritaActivity : AppCompatActivity(),
    ListBeritaInterface {

    private val viewModel by lazy {
        ViewModelProvider(this).get(BeritaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityListBeritaBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_list_berita)

        viewModel.listBeritaInterface = this
        viewModel.getAllBerita()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllBerita()
        }

        initToolbar()

        binding.viewmodel = viewModel
    }

    private fun initToolbar() {
        toolbar.title = "Berita"
        toolbar.navigationIcon?.setTint(getColor(android.R.color.white))
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onGetData(data: LiveData<Resource<ArrayList<BeritaModel>>>) {
        data.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    swipeRefreshLayout.isRefreshing = false
                    rvBerita.visibility = View.VISIBLE
                    setRecyclerView(it.data!!)
                }
                Status.ERROR -> {
                    swipeRefreshLayout.isRefreshing = false
                    toast(it.message!!)
                    rvBerita.visibility = View.GONE
                }
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                else -> toast("Nothing")
            }
        })
    }

    private fun setRecyclerView(data: ArrayList<BeritaModel>) {
        rvBerita.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DaftarBeritaRVAdapter(data, viewModel)
            (adapter as DaftarBeritaRVAdapter).notifyDataSetChanged()
        }
    }

    override fun onCardClicked(data: BeritaModel) {
        val intent = Intent(this@ListBeritaActivity, DetailBeritaActivity::class.java)
        intent.putExtra(DATA_BERITA,data)
        startActivity(intent)
    }

}
