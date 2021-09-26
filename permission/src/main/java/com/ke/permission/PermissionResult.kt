package com.ke.permission

data class PermissionResult(
    /**
     * 权限名称
     */
    val name: String,
    /**
     * 请求结果
     */
    val granted: Boolean,

    val shouldShowRequestPermissionRationale: Boolean
)
