package com.ke.permission.demo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.ke.permission.KePermission
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

    }



    companion object {
        val permissionList1 = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}