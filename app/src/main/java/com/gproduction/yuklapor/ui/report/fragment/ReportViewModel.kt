package com.gproduction.yuklapor.ui.report.fragment

import androidx.lifecycle.ViewModel
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository

class ReportViewModel : ViewModel(){
    var reportInterface: ReportInterface?=null

    fun getDataLaporan(){
        val data = LaporkanRepository().getAllLaporan()
        reportInterface?.onGetData(data)
    }

    fun filterData(data:ArrayList<LaporkanModel>?){
        val listBelumDiproses = ArrayList<LaporkanModel>()
        val listDiproses = ArrayList<LaporkanModel>()
        val listSelesai = ArrayList<LaporkanModel>()

        data?.let {
            if (listBelumDiproses.size > 0) listBelumDiproses.clear()
            if (listDiproses.size > 0) listDiproses.clear()
            if (listSelesai.size > 0) listDiproses.clear()

            for (i in it)
                when (i.status) {
                    0 -> listBelumDiproses.add(i)
                    1 -> listDiproses.add(i)
                    2 -> listSelesai.add(i)
                }
        }
        val list = ArrayList<DataEntry>()
        list.add(ValueDataEntry("Belum Disetujui", listBelumDiproses.size))
        list.add(ValueDataEntry("Diproses", listDiproses.size))
        list.add(ValueDataEntry("Selesai",listSelesai.size))

        reportInterface?.showChart(list)
    }
}