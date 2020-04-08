package com.gproduction.yuklapor.ui.laporkan


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.databinding.BottomSheetChoosePhotoBinding
import com.gproduction.yuklapor.databinding.FragmentLaporkanBinding
import com.gproduction.yuklapor.tools.REQUEST_IMAGE_CAPTURE
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.tools.toast
import kotlinx.android.synthetic.main.fragment_laporkan.*

/**
 * A simple [Fragment] subclass.
 */
class LaporkanFragment : Fragment(),LaporkanInterface {

    private val viewModel by lazy {
        ViewModelProvider(this).get(LaporkanViewModel::class.java)
    }

    private val bottomSheet by lazy {
        BottomSheetDialog(requireContext())
    }

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding:FragmentLaporkanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_laporkan,container,false)

        binding.viewmodel = viewModel

        viewModel.laporkanInterface = this

        initBottomSheetDialog()

        return binding.root
    }

    companion object {
        fun newInstance(): LaporkanFragment {
            val fragment = LaporkanFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun initBottomSheetDialog(){
        val binding:BottomSheetChoosePhotoBinding = DataBindingUtil.inflate(layoutInflater,R.layout.bottom_sheet_choose_photo,null,false)
        binding.viewmodel = viewModel

        bottomSheet.setContentView(binding.root)
    }


    override fun onFailed() {
        requireContext().toast("GAGAL MZ")
    }

    override fun onLaporkan(data:LiveData<Int>) {
       data.observe(this, Observer {
           when(it){
               1 -> requireContext().toast("Data Berhasil Diajukkan!")
               2 -> requireContext().toast("Data Gagal Diajukkan!")
           }
       })
    }

    override fun onAddPhotoClicked() {
        bottomSheet.show()
    }

    override fun onCameraClicked() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            icAdd.visibility = View.GONE
            val imageBitmap = data?.extras?.get("data") as Bitmap
            viewModel.photo = imageBitmap
            sharedPreferences.getUid()?.let {
                viewModel.uid = it
            }

            sharedPreferences.getNik()?.let {
                viewModel.nik = it
            }
            addImage.setImageBitmap(imageBitmap)
        }
    }

}
