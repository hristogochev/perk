package io.github.hristogochev.perk.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
public actual fun rememberPermissionChecker(): PermissionChecker {
    val context = LocalContext.current
    return remember(context) {
        object : PermissionChecker {
            override fun isPermissionGranted(permission: Permission, onResult: (Boolean) -> Unit) {
                val granted = context.isAndroidPermissionGranted(permission)
                onResult(granted)
            }
        }
    }
}