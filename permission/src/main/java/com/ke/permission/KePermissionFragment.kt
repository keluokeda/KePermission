package com.ke.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

internal class KePermissionFragment : Fragment() {


    var resultHandler: ((List<PermissionResult>) -> Unit)? = null

    private lateinit var launcher: ActivityResultLauncher<Array<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launcher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

                resultHandler?.invoke(result.map {
                    PermissionResult(
                        it.key,
                        it.value,
                        shouldShowRequestPermissionRationale(it.key)
                    )
                }
                )

            }
    }


    fun request(permissions: Array<String>) {
        launcher.launch(permissions)
    }


}