package com.ke.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.parcelize.Parcelize

class KePermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_activity_ke)

        val requestPermissionInfo = intent.getParcelableExtra<RequestPermissionInfo>("info")!!


        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

                val intent = Intent()

                intent.putExtra(
                    "result",
                    RequestPermissionResult(
                        requestPermissionInfo.key,
                        result
                    )
                )
                setResult(RESULT_OK, intent)
                finish()
            }

        launcher.launch(requestPermissionInfo.permissions.toTypedArray())
    }
}

@Parcelize
data class RequestPermissionInfo(
    val key: String,
    val permissions: List<String>,
    val title: String,
    val desc: String,
) : Parcelable

@Parcelize
data class RequestPermissionResult(
    val key: String,
    val results: Map<String, Boolean>,
) : Parcelable

class RequestPermissionsWthIntroduce :
    ActivityResultContract<RequestPermissionInfo, RequestPermissionResult>() {
    override fun createIntent(context: Context, input: RequestPermissionInfo): Intent {
        val intent = Intent(context, KePermissionActivity::class.java)
        intent.putExtra("info", input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): RequestPermissionResult {
        return intent!!.getParcelableExtra("result")!!
    }

    override fun getSynchronousResult(
        context: Context,
        input: RequestPermissionInfo,
    ): SynchronousResult<RequestPermissionResult>? {
        val permissions = input.permissions
        if (permissions.isEmpty()) {
            return SynchronousResult(RequestPermissionResult(input.key, emptyMap()))
        }


        val allGranted = permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }




        return if (allGranted) {
            SynchronousResult(
                RequestPermissionResult(
                    input.key,
                    permissions.associateWith { true })
            )
        } else null
    }

}