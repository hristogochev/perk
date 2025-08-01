package io.github.hristogochev.perk.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Build

public fun Permission.Companion.fromPlatformPermission(platformPermission: String): Permission? {
    if (platformPermission == Manifest.permission.RECORD_AUDIO) {
        return Permission.Microphone
    }
    if (platformPermission == Manifest.permission.POST_NOTIFICATIONS) {
        return Permission.PostNotifications
    }
    if (platformPermission == Manifest.permission.CAMERA) {
        return Permission.Camera
    }
    return null
}

public fun Context.isPermissionPermanentlyDeclined(permission: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return false
    }

    val activity = getActivity() ?: run {
        println("Unable to get activity")
        return false
    }

    return !activity.shouldShowRequestPermissionRationale(permission)
}

private fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

public fun Context.isAndroidPermissionGranted(permission: Permission): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }
    return when (permission) {
        Permission.Microphone -> {
            checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        }

        Permission.PostNotifications -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else true
        }

        Permission.Camera -> {
            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        }
    }
}
