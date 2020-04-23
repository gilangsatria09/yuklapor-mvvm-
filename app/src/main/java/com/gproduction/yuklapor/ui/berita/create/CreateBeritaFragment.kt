package com.gproduction.yuklapor.ui.berita.create


import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.Status
import com.gproduction.yuklapor.databinding.FragmentCreateBeritaBinding
import com.gproduction.yuklapor.tools.*
import com.gproduction.yuklapor.tools.CustomView.Companion.dialogCustom
import com.gproduction.yuklapor.ui.home.HomeActivityAdmin
import com.gproduction.yuklapor.ui.berita.BeritaViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.bottom_sheet_choose_photo.*
import kotlinx.android.synthetic.main.fragment_laporkan.*

/**
 * A simple [Fragment] subclass.
 */
class CreateBeritaFragment : Fragment(), CreateBeritaInterface {

    private val viewModel by lazy {
        ViewModelProvider(this).get(BeritaViewModel::class.java)
    }

    private val bottomSheet by lazy {
        CustomView.bottomSheet(requireContext())
    }

    private val dialog by lazy {
        dialogCustom(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCreateBeritaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_berita, container, false)
        binding.viewmodel = viewModel
        viewModel.createBeritaInterface = this

        initBottomSheet()

        return binding.root
    }

    private fun initBottomSheet() {
        bottomSheet.imgCamera.setOnClickListener {
            checkPermissions()
        }
        bottomSheet.imgGallery.setOnClickListener {
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
    }

    private fun openCamera() {
        bottomSheet.dismiss()
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            icAdd.visibility = View.GONE
            val imageBitmap = data?.extras?.get("data") as Bitmap
            viewModel.photo = imageBitmap
            addImage.setImageBitmap(imageBitmap)
        }
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){
            data?.let {
                icAdd.visibility = View.GONE
                val bitmap = getImageBitmap(requireActivity().contentResolver,it.data!!)
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
    override fun onAddPhotoClicked() {
        bottomSheet.show()
    }

    override fun onFailed() {
        requireContext().toast("Mohon isi data yang ada!")
    }

    override fun onButtonBeritaClicked(data: LiveData<Resource<Boolean>>) {
        data.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    dialog.dismiss()
                    requireContext().toast("Berhasil!")
                    val intent = Intent(requireContext(), HomeActivityAdmin::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity()
                }
                Status.ERROR -> {
                    requireContext().toast("Gagal!")
                    dialog.dismiss()
                }
                Status.LOADING -> dialog.show()
                else -> {}
            }
        })
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
