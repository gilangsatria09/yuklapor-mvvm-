package com.gproduction.yuklapor.ui.berita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gproduction.yuklapor.BR
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.BeritaModel
import com.gproduction.yuklapor.databinding.ItemBeritaBinding
import com.gproduction.yuklapor.ui.berita.BeritaViewModel

class DaftarBeritaRVAdapter(private val listBerita:ArrayList<BeritaModel>, private val viewModel:BeritaViewModel) : RecyclerView.Adapter<DaftarBeritaRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemBeritaBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_berita,parent,false)
        binding.viewmodel = viewModel
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listBerita.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBerita[position])
    }

    class ViewHolder(private val binding:ItemBeritaBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:BeritaModel){
            binding.setVariable(BR.model,data)
            binding.executePendingBindings()
        }
    }

}