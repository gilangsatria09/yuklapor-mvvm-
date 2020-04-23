package com.gproduction.yuklapor.ui.report.fragment

import androidx.lifecycle.LiveData
import com.anychart.chart.common.dataentry.DataEntry
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel

interface ReportInterface {
    fun onGetData(data:LiveData<Resource<ArrayList<LaporkanModel>>>)
    fun showChart(data:ArrayList<DataEntry>)
}