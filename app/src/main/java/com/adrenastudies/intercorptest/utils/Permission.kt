package com.adrenastudies.intercorptest.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.adrenastudies.moneelyte.interfaces.PermissionResponse
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class Permission {

    companion object {

        fun checkPermisionStatus(ctx: Context, permission: String): Boolean {
            return ActivityCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED
        }

        fun getSimplePermission(act: Activity, permision: String, callback: PermissionResponse) {
            Dexter.withContext(act)
                .withPermission(permision)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        callback.onSuccess()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        callback.onRefuse()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
        }

        fun getSimplePermission(act: Activity, callback: PermissionResponse) {
            Dexter.withContext(act)
                    .withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                     android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    callback.onSuccess()
                }

                override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                    p1!!.continuePermissionRequest()
                }

            }).check()
        }

    }
}