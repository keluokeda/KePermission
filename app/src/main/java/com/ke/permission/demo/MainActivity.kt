package com.ke.permission.demo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.ke.permission.KePermission
import com.ke.permission.RequestPermissionInfo
import com.ke.permission.RequestPermissionsWthIntroduce
import com.ke.permission.demo.databinding.ActivityMainBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.addLogAdapter(AndroidLogAdapter())


        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkPermissions.setOnClickListener {
            permissionList1.forEach { permission ->
                val result = ActivityCompat.checkSelfPermission(this, permission) to
                        //如果用户点了拒绝 这个为true
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)


                Logger.d("权限名称 $permission 权限状态 $result")
            }
        }


        binding.requestPermissions.setOnClickListener {


            KePermission(supportFragmentManager)
                .requestPermissions(permissionList1) { list ->
                    list.forEach {
                        Logger.d(it)
                    }
                }
        }

        binding.requestPermissionsWithCoroutine.setOnClickListener {

            lifecycleScope.launch {
                val permission = KePermission(supportFragmentManager)
                val list = permission.requestPermissions(permissionList1)
                list.forEach {
                    Logger.d(it)
                }
            }
        }


        val launcher = registerForActivityResult(RequestPermissionsWthIntroduce()) {

            Logger.d("请求权限结果 $it")
        }

        binding.requestPermissionsWithDesc.setOnClickListener {

            val shouldShowRequestPermissionRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

            Logger.d("shouldShowRequestPermissionRationale $shouldShowRequestPermissionRationale")
            if (shouldShowRequestPermissionRationale
            ) {
//                Logger.d("已经拒绝且不会弹窗了")

                AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("需要定位权限")
                    .setPositiveButton("授权", null)
                    .show()
            } else {
                launcher.launch(
                    RequestPermissionInfo(
                        "",
                        listOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        "相机使用权限说明:",
                        "为了使用相机"
                    )
                )
            }

        }


    }


    companion object {
        val permissionList1 = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS,
        )
    }
}