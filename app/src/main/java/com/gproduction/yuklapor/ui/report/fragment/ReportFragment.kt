package com.gproduction.yuklapor.ui.report.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.charts.Pie
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout

import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.tools.toast
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : Fragment(),ReportInterface {

    companion object{
        fun newInstance() : ReportFragment{
            val fragment = ReportFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var pieChart: Pie

    private val viewModel by lazy{
        ViewModelProvider(this).get(ReportViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.reportInterface = this
        viewModel.getDataLaporan()

        pieChart = AnyChart.pie()

        pieChart.apply {
            title("Data Laporan Masyarakat")
            labels().position("outside")
            legend().title().enabled(true)
            legend().title().text("Status").padding(0,0,10,0)
            legend().position("center-bottom").itemsLayout(LegendLayout.VERTICAL).align(Align.CENTER)
        }
    }

    override fun onGetData(data: LiveData<Resource<ArrayList<LaporkanModel>>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.filterData(it.data)
                }
                Status.ERROR -> requireContext().toast("${it.message}")
                else -> {}
            }
        })
    }

    override fun showChart(data: ArrayList<DataEntry>) {
        pieChart.data(data)
        anyChart.setChart(pieChart)
    }



}
