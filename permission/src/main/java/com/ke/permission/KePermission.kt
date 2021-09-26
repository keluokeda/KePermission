package com.ke.permission

import androidx.fragment.app.FragmentManager
import kotlin.coroutines.suspendCoroutine

class KePermission(private val fragmentManager: FragmentManager) {




    fun requestPermissions(permissions: Array<String>, callback: (List<PermissionResult>) -> Unit) {
        getFragment().resultHandler = callback
        getFragment().request(permissions)
    }

    suspend fun requestPermissions(permissions: Array<String>): List<PermissionResult> {

        val fragment = getFragment()
        return suspendCoroutine {
            fragment.resultHandler = { list ->
                it.resumeWith(Result.success(list))
            }
            fragment.request(permissions)
        }
    }

    private fun getFragment(): KePermissionFragment {
        var fragment = fragmentManager.findFragmentByTag(TAG) as? KePermissionFragment

        if (fragment == null) {
            fragment = KePermissionFragment()
            fragmentManager.beginTransaction().add(fragment, TAG).commitNow()
        }

        return fragment
    }


    companion object {
        private val TAG = KePermissionFragment::class.java.name
    }

}
