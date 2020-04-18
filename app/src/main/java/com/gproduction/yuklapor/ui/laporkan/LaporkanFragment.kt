package com.gproduction.yuklapor.ui.laporkan


import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.databinding.BottomSheetChoosePhotoBinding
import com.gproduction.yuklapor.databinding.FragmentLaporkanBinding
import com.gproduction.yuklapor.tools.*
import com.gproduction.yuklapor.ui.home.HomeActivityMasyarakat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_laporkan.*

/**
 * A simple [Fragment] subclass.
 */
class LaporkanFragment : Fragment(), LaporkanInterface {

    private val viewModel by lazy {
        ViewModelProvider(this).get(LaporkanViewModel::class.java)
    }

    private val bottomSheet by lazy {
        BottomSheetDialog(requireContext())
    }

    private val sharedPreferences by lazy {
        SharedPreferences(requireContext())
    }

    private val dialog by lazy {
        CustomDialog(requireContext())
    }


    private lateinit var binding: FragmentLaporkanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_laporkan, container, false)

        binding.viewmodel = viewModel

        viewModel.laporkanInterface = this

        initBottomSheetDialog()


        // Set ViewModel UID ,NIK, and Nama Pembuat
        sharedPreferences.getUid()?.let {
            viewModel.uid = it
        }

        sharedPreferences.getNik()?.let {
            viewModel.nik = it
        }

        viewModel.namaPembuat = sharedPreferences.getNama()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val laporkanModel: LaporkanModel? = arguments!!.getParcelable(DATA_LAPORKAN)
            initDetailDataEdit(laporkanModel)
        }
    }

    private fun initBottomSheetDialog() {
        val binding: BottomSheetChoosePhotoBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.bottom_sheet_choose_photo, null, false)
        binding.viewmodel = viewModel

        bottomSheet.setContentView(binding.root)
    }

    private fun initDetailDataEdit(data: LaporkanModel?) {
        data?.let {
            binding.model = it
            viewModel.judul = it.judul
            viewModel.content = it.content
            viewModel.status = 1

            Glide.with(requireContext()).load(it.imageUrl).into(addImage)
            icAdd.visibility = View.GONE
            buttonLaporkan.text = getString(R.string.edit_laporan)
        }

    }

    override fun onFailed(message: String) {
        requireContext().toast(message)
    }

    override fun onLaporkan(data: LiveData<Resource<Int>>) {
        data.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    requireContext().toast("Berhasil!")
                    val intent = Intent(requireContext(), HomeActivityMasyarakat::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity()
                }
                Status.ERROR -> {
                    requireContext().toast("Gagal!")
                    dialog.dismiss()
                }
                Status.LOADING -> dialog.show()
                else -> Log.d("Nothing", "nothing")
            }
        })
    }

    override fun onAddPhotoClicked() {
        bottomSheet.show()
    }

    override fun onCameraClicked() {
        checkPermissions()
    }

    private fun openCamera() {
        bottomSheet.dismiss()
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onGalleryClicked() {
        bottomSheet.dismiss()
        Intent().apply {
            type = IMAGE_TYPE
            action = Intent.ACTION_GET_CONTENT
        }.also { galleryIntent ->
            galleryIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            icAdd.visibility = View.GONE
            val imageBitmap = data?.extras?.get("data") as Bitmap
            viewModel.photo = imageBitmap
            addImage.setImageBitmap(imageBitmap)
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            data?.let {
                icAdd.visibility = View.GONE
                val bitmap = getImageBitmap(requireActivity().contentResolver, it.data!!)
                addImage.setImageBitmap(bitmap)
                viewModel.photo = bitmap
            }
        }
    }

    private fun getImageBitmap(contentResolver: ContentResolver, path: Uri): Bitmap {
        @Suppress("DEPRECATION") return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, path))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, path)
        }
    }


    companion object {
        fun newInstance(): LaporkanFragment {
            val fragment = LaporkanFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(data: LaporkanModel): LaporkanFragment {
            val fragment = LaporkanFragment()
            val args = Bundle()
            args.putParcelable(DATA_LAPORKAN, data)
            fragment.arguments = args
            return fragment
        }
    }

    private fun checkPermissions() {
        Dexter
            .withContext(requireContext())
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    openCamera()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    //Do Nothing
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

            }).check()
    }

}
