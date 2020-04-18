package com.gproduction.yuklapor.ui.daftarlaporan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gproduction.yuklapor.BR
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.databinding.ItemLaporanMainBinding
import com.gproduction.yuklapor.ui.daftarlaporan.DaftarLaporanViewModel

class BelumDiprosesRVAdapter(private val listLaporkan:ArrayList<LaporkanModel>, private val viewModel:DaftarLaporanViewModel)
    :RecyclerView.Adapter<BelumDiprosesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemLaporanMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_laporan_main,parent,false)
        binding.viewmodel = viewModel
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listLaporkan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listLaporkan[position])
    }

    class ViewHolder(private val binding:ItemLaporanMainBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:LaporkanModel){
            binding.setVariable(BR.model,item)
            binding.executePendingBindings()
        }
    }


}