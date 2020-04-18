package com.gproduction.yuklapor.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gproduction.yuklapor.BR
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.databinding.ItemLaporanHomeBinding
import com.gproduction.yuklapor.ui.home.fragment.HomeViewModel
import kotlin.math.min

class HomeRVAdapter (private val laporanList:ArrayList<LaporkanModel>,private val viewModel: HomeViewModel) : RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemLaporanHomeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_laporan_home,parent,false)
        binding.viewmodel = viewModel
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = min(laporanList.size,5)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(laporanList[position])
    }

    class ViewHolder(private val binding: ItemLaporanHomeBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LaporkanModel){
            binding.setVariable(BR.model,item)
            binding.executePendingBindings()
        }
    }
}