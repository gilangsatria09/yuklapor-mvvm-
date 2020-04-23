package com.gproduction.yuklapor.ui.home.admin


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.databinding.FragmentHomeAdminBinding
import com.gproduction.yuklapor.tools.DATA_LAPORKAN
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.ui.berita.list.ListBeritaActivity
import com.gproduction.yuklapor.ui.daftarlaporan.main.DaftarLaporanActivity
import com.gproduction.yuklapor.ui.detaillaporan.DetailLaporanActivity
import com.gproduction.yuklapor.ui.home.adapter.HomeRVAdapter
import com.gproduction.yuklapor.ui.home.adapter.OnClickInterface
import com.gproduction.yuklapor.ui.report.ReportActivty
import kotlinx.android.synthetic.main.fragment_home_admin.*


/**
 * A simple [Fragment] subclass.
 */
class HomeAdminFragment : Fragment(), HomeAdminInterface,OnClickInterface {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeAdminViewModel::class.java)
    }

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentHomeAdminBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_admin, container, false
        )
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.homeAdminInterface = this
        viewModel.getAllData()
        cardBg.background = requireActivity().getDrawable(R.drawable.bg_card_rounded)
        namaUser.text = sharedPreferences.getNama()
        if(sharedPreferences.getRole() == 2){
            buttonReport.visibility = View.GONE
        }else{
            buttonReport.visibility = View.VISIBLE
        }
    }

    private fun settingRecyclerView(list:ArrayList<LaporkanModel>?){
        list?.let {
            it.reverse()
            rvLaporanHome.apply {
                layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false)
                adapter = HomeRVAdapter(it,this@HomeAdminFragment)
            }
        }
    }

    override fun onDaftarLaporanClicked() {
        val intent = Intent(requireContext(), DaftarLaporanActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
    }

    override fun onBeritaClicked() {
        val intent = Intent(
            requireContext(),
            ListBeritaActivity::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
    }

    override fun onGetAllData(data: LiveData<Resource<ArrayList<LaporkanModel>>>) {
        data.observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    settingRecyclerView(it.data)
                }
                else -> {
                }
            }
        })
    }

    override fun onReportClicked() {
        val intent = Intent(requireContext(),ReportActivty::class.java)
        startActivity(intent)
    }

    override fun onCardClicked(data: LaporkanModel?) {
        val intent = Intent(requireContext(), DetailLaporanActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DATA_LAPORKAN,data)

        intent.apply {
            putExtras(bundle)
        }

        startActivity(intent)
    }



}
