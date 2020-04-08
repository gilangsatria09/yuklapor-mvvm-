package com.gproduction.yuklapor.ui.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.UserModel
import com.gproduction.yuklapor.databinding.FragmentHomeBinding
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.toast
import com.gproduction.yuklapor.ui.daftarlaporan.main.DaftarLaporanActivity
import com.gproduction.yuklapor.ui.home.adapter.HomeRVAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(),HomeInterface {

    private lateinit var viewModel: HomeViewModel

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel


        viewModel.homeInterface = this

        sharedPreferences.getUid()?.let {
            viewModel.getUserData(it)
            viewModel.getAllDataLaporan(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        cardBg.background = requireActivity().getDrawable(R.drawable.bg_card_rounded)
    }

    private fun settingRecyclerView(list:ArrayList<LaporkanModel>){
        list.reverse()
        rvLaporanHome.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = HomeRVAdapter(list,viewModel)
        }
    }

    companion object{
        fun newInstance() : HomeFragment{
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getUserData(userData: LiveData<UserModel>) {
        userData.observe(this, Observer {
            if (it != null){
                namaUser.text = it.nama
            }
            else{
                requireContext().toast("GAGAL")
            }
        })
    }

    override fun getAllDataLaporan(data: LiveData<Resource<ArrayList<LaporkanModel>>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    settingRecyclerView(it.data!!)
                }
            }
        })
    }

    override fun onCardClicked(model: LaporkanModel) {
        requireContext().toast(model.judul!!)
    }

    override fun onDaftarLaporanClicked() {
        val intent = Intent(requireContext(), DaftarLaporanActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireContext().startActivity(intent)
    }

}
